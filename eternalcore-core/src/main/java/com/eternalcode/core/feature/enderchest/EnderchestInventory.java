package com.eternalcode.core.feature.enderchest;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.enderchest.event.EnderchestOpenEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.StorageGui;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Service
class EnderchestInventory {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";
    private static final String PAGE_PLACEHOLDER = "{PAGE}";
    private static final String PAGES_PLACEHOLDER = "{PAGES}";
    private static final String NEXT_PLACEHOLDER = "{NEXT}";
    private static final String PREVIOUS_PLACEHOLDER = "{PREVIOUS}";
    private static final String TIME_PLACEHOLDER = "{TIME}";

    private final Map<PageKey, PageSession> openPages = new ConcurrentHashMap<>();
    private final Delay<UUID> pageSwitchDelay;

    private final EnderchestManager enderchestManager;
    private final EnderchestSettings settings;
    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final MinecraftScheduler scheduler;
    private final EventCaller eventCaller;

    @Inject
    EnderchestInventory(
        EnderchestManager enderchestManager,
        EnderchestSettings settings,
        NoticeService noticeService,
        MiniMessage miniMessage,
        MinecraftScheduler scheduler,
        EventCaller eventCaller
    ) {
        this.enderchestManager = enderchestManager;
        this.settings = settings;
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
        this.eventCaller = eventCaller;
        this.pageSwitchDelay = Delay.withDefault(() -> settings.pages().switchDelay());
    }

    void openPage(Player viewer, OfflinePlayer owner, int page) {
        this.loadAndOpenPage(viewer, owner.getUniqueId(), owner.getName(), page);
    }

    void saveOpenPages() {
        this.openPages.forEach((key, session) -> {
            Enderchest enderchest = session.enderchest();

            updatePageFromInventory(key, enderchest, session.gui().getInventory());
            this.enderchestManager.saveEnderchest(enderchest);
        });
    }

    boolean isOpenPage(Inventory inventory) {
        for (PageSession session : this.openPages.values()) {
            if (session.gui().getInventory().equals(inventory)) {
                return true;
            }
        }

        return false;
    }

    void closeAllPages() {
        for (PageSession session : new ArrayList<>(this.openPages.values())) {
            for (HumanEntity viewer : new ArrayList<>(session.gui().getInventory().getViewers())) {
                viewer.closeInventory();
            }
        }
    }

    private void loadAndOpenPage(Player viewer, UUID ownerUniqueId, @Nullable String ownerName, int page) {
        this.enderchestManager.loadEnderchest(ownerUniqueId, ownerName)
            .thenAccept(enderchest -> this.scheduler.run(viewer, () -> this.openLoadedPage(viewer, enderchest, page)))
            .exceptionally(FutureHandler::handleException);
    }

    private void openLoadedPage(Player viewer, Enderchest enderchest, int page) {
        UUID ownerUniqueId = enderchest.getOwnerUniqueId();

        if (this.settings.enderchestsBlocked() || !this.settings.replaceVanillaEnderchest()) {
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        if (!this.enderchestManager.isLoaded(ownerUniqueId, enderchest)) {
            this.loadAndOpenPage(viewer, ownerUniqueId, null, page);
            return;
        }

        if (enderchest.isImporting()) {
            this.sendEnderchestInUseNotice(viewer, enderchest);
            return;
        }

        if (!viewer.getUniqueId().equals(ownerUniqueId) && enderchest.getUsedPages() == 0) {
            this.sendEnderchestEmptyNotice(viewer, enderchest);
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        this.showPage(viewer, enderchest, page);
    }

    private void showPage(Player viewer, Enderchest enderchest, int page) {
        UUID ownerUniqueId = enderchest.getOwnerUniqueId();
        int pages = this.enderchestManager.getAccessiblePages(enderchest);

        if (page < EnderchestLayout.FIRST_PAGE || page > pages) {
            this.sendPageUnavailableNotice(viewer, page, pages);
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        EnderchestOpenEvent event = this.eventCaller.callEvent(new EnderchestOpenEvent(viewer.getUniqueId(), ownerUniqueId, page));
        if (event.isCancelled()) {
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        if (this.settings.sharedViewingBlocked() && enderchest.isViewedByOther(viewer.getUniqueId())) {
            this.takeOverFromOtherViewers(viewer, ownerUniqueId);
            this.sendEnderchestInUseNotice(viewer, enderchest);
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        PageKey key = new PageKey(ownerUniqueId, page);
        PageSession session = this.claimSession(key, enderchest, pages);
        StorageGui gui = session.gui();
        Inventory inventory = gui.getInventory();

        if (viewer.getOpenInventory().getTopInventory() == inventory) {
            this.releaseSession(key);
            return;
        }

        this.refreshNavigationItems(session, key, enderchest, pages);
        enderchest.attachViewer(viewer.getUniqueId());
        gui.open(viewer);

        if (viewer.getOpenInventory().getTopInventory() != inventory) {
            enderchest.detachViewer();
            this.releaseSession(key);
            this.enderchestManager.unloadIdleEnderchest(ownerUniqueId);
            return;
        }

        this.sendPageOpenedNotice(viewer, enderchest, page, pages);
    }

    private void takeOverFromOtherViewers(Player viewer, UUID ownerUniqueId) {
        if (viewer.getUniqueId().equals(ownerUniqueId)) {
            return;
        }

        for (Map.Entry<PageKey, PageSession> openPage : this.openPages.entrySet()) {
            if (!openPage.getKey().ownerUniqueId().equals(ownerUniqueId)) {
                continue;
            }

            for (HumanEntity other : new ArrayList<>(openPage.getValue().gui().getInventory().getViewers())) {
                this.scheduler.run(other, other::closeInventory);
            }
        }
    }

    private PageSession claimSession(PageKey key, Enderchest enderchest, int pages) {
        PageSession session = this.openPages.computeIfAbsent(key,
            pageKey -> new PageSession(this.createGui(pageKey, enderchest, pages), enderchest, pages));

        session.claims++;
        return session;
    }

    private void releaseSession(PageKey key) {
        PageSession session = this.openPages.get(key);
        if (session == null) {
            return;
        }

        session.claims--;

        if (session.claims <= 0) {
            this.openPages.remove(key, session);
        }
    }

    private StorageGui createGui(PageKey key, Enderchest enderchest, int pages) {
        EnderchestLayout layout = enderchest.getLayout();
        int page = key.page();

        StorageGui gui = Gui.storage()
            .title(this.miniMessage.deserialize(this.fillPlaceholders(this.settings.pages().title(), key, enderchest, pages)))
            .rows(layout.rows())
            .create();

        Inventory inventory = gui.getInventory();
        ItemStack[] contents = enderchest.getPageContents(page);
        for (int index = 0; index < contents.length; index++) {
            inventory.setItem(layout.storageSlot(page, index), contents[index]);
        }

        this.setNavigationItems(gui, key, enderchest, pages);
        gui.addSlotAction(layout.nextPageSlot(), event -> event.setCancelled(true));

        if (page > EnderchestLayout.FIRST_PAGE) {
            gui.addSlotAction(layout.previousPageSlot(), event -> event.setCancelled(true));
        }

        gui.setDragAction(event -> {
            if (event.getRawSlots().stream().anyMatch(slot -> layout.isNavigationSlot(page, slot))) {
                event.setCancelled(true);
            }
        });
        gui.setCloseGuiAction(event -> this.onClose(key, enderchest, event));

        return gui;
    }

    private void refreshNavigationItems(PageSession session, PageKey key, Enderchest enderchest, int pages) {
        if (session.renderedPages == pages) {
            return;
        }

        session.renderedPages = pages;
        this.setNavigationItems(session.gui(), key, enderchest, pages);
    }

    private void setNavigationItems(StorageGui gui, PageKey key, Enderchest enderchest, int pages) {
        EnderchestSettings.NavigationSettings navigation = this.settings.pages().navigation();
        EnderchestLayout layout = enderchest.getLayout();
        int page = key.page();

        gui.updateItem(layout.nextPageSlot(), this.createNavigationItem(navigation.nextPage(), key, enderchest, pages)
            .asGuiItem(event -> this.onNavigationClick(event, key.ownerUniqueId(), nextPage(page), pages)));

        if (page > EnderchestLayout.FIRST_PAGE) {
            gui.updateItem(layout.previousPageSlot(), this.createNavigationItem(navigation.previousPage(), key, enderchest, pages)
                .asGuiItem(event -> this.onNavigationClick(event, key.ownerUniqueId(), previousPage(page), pages)));
        }
    }

    private ItemBuilder createNavigationItem(EnderchestSettings.ItemSettings item, PageKey key, Enderchest enderchest, int pages) {
        List<Component> lore = item.lore().stream()
            .map(line -> this.fillPlaceholders(line, key, enderchest, pages))
            .map(line -> AdventureUtil.resetItalic(this.miniMessage.deserialize(line)))
            .toList();

        ItemBuilder builder = ItemBuilder.from(item.material())
            .lore(lore)
            .glow(item.glow());

        if (item.name().isEmpty()) {
            return builder;
        }

        return builder.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(this.fillPlaceholders(item.name(), key, enderchest, pages))));
    }

    private void onNavigationClick(InventoryClickEvent event, UUID ownerUniqueId, int page, int pages) {
        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (page > pages) {
            player.closeInventory();
            this.sendPageUnavailableNotice(player, page, pages);
            return;
        }

        UUID playerUniqueId = player.getUniqueId();
        if (this.pageSwitchDelay.hasDelay(playerUniqueId)) {
            this.sendPageSwitchDelayNotice(player, this.pageSwitchDelay.getRemaining(playerUniqueId));
            return;
        }

        this.pageSwitchDelay.markDelay(playerUniqueId);
        this.loadAndOpenPage(player, ownerUniqueId, null, page);
    }

    private void onClose(PageKey key, Enderchest enderchest, InventoryCloseEvent event) {
        updatePageFromInventory(key, enderchest, event.getInventory());
        enderchest.detachViewer();
        this.releaseSession(key);

        this.enderchestManager.saveEnderchest(enderchest);
        this.enderchestManager.unloadIdleEnderchest(key.ownerUniqueId());
    }

    private void sendPageOpenedNotice(Player viewer, Enderchest enderchest, int page, int pages) {
        boolean ownEnderchest = viewer.getUniqueId().equals(enderchest.getOwnerUniqueId());

        this.noticeService.create()
            .notice(translation -> ownEnderchest
                ? translation.enderchest().openedEnderchestPage()
                : translation.enderchest().openedTargetPlayerEnderchest())
            .placeholder(PLAYER_PLACEHOLDER, enderchest.getOwnerName())
            .placeholder(PAGE_PLACEHOLDER, String.valueOf(page))
            .placeholder(PAGES_PLACEHOLDER, String.valueOf(pages))
            .player(viewer.getUniqueId())
            .send();
    }

    private void sendPageUnavailableNotice(Player viewer, int page, int pages) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().enderchestPageUnavailable())
            .placeholder(PAGE_PLACEHOLDER, String.valueOf(page))
            .placeholder(PAGES_PLACEHOLDER, String.valueOf(pages))
            .player(viewer.getUniqueId())
            .send();
    }

    private void sendPageSwitchDelayNotice(Player viewer, Duration remaining) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().enderchestPageSwitchDelay())
            .placeholder(TIME_PLACEHOLDER, DurationUtil.format(remaining, true))
            .player(viewer.getUniqueId())
            .send();
    }

    private void sendEnderchestInUseNotice(Player viewer, Enderchest enderchest) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().enderchestInUse())
            .placeholder(PLAYER_PLACEHOLDER, enderchest.getOwnerName())
            .player(viewer.getUniqueId())
            .send();
    }

    private void sendEnderchestEmptyNotice(Player viewer, Enderchest enderchest) {
        this.noticeService.create()
            .notice(translation -> translation.enderchest().playerEnderchestEmpty())
            .placeholder(PLAYER_PLACEHOLDER, enderchest.getOwnerName())
            .player(viewer.getUniqueId())
            .send();
    }

    private String fillPlaceholders(String text, PageKey key, Enderchest enderchest, int pages) {
        return text
            .replace(PLAYER_PLACEHOLDER, enderchest.getOwnerName())
            .replace(PAGE_PLACEHOLDER, String.valueOf(key.page()))
            .replace(PAGES_PLACEHOLDER, String.valueOf(pages))
            .replace(NEXT_PLACEHOLDER, String.valueOf(nextPage(key.page())))
            .replace(PREVIOUS_PLACEHOLDER, String.valueOf(previousPage(key.page())));
    }

    private static void updatePageFromInventory(PageKey key, Enderchest enderchest, Inventory inventory) {
        EnderchestLayout layout = enderchest.getLayout();
        int page = key.page();

        ItemStack[] contents = new ItemStack[layout.capacityOf(page)];
        for (int index = 0; index < contents.length; index++) {
            contents[index] = inventory.getItem(layout.storageSlot(page, index));
        }

        enderchest.updatePage(page, contents);
    }

    private static int nextPage(int page) {
        return page + 1;
    }

    private static int previousPage(int page) {
        return Math.max(EnderchestLayout.FIRST_PAGE, page - 1);
    }

    private record PageKey(UUID ownerUniqueId, int page) {}

    private static final class PageSession {

        private final StorageGui gui;
        private final Enderchest enderchest;

        private int claims;
        private int renderedPages;

        private PageSession(StorageGui gui, Enderchest enderchest, int renderedPages) {
            this.gui = gui;
            this.enderchest = enderchest;
            this.renderedPages = renderedPages;
        }

        private StorageGui gui() {
            return this.gui;
        }

        private Enderchest enderchest() {
            return this.enderchest;
        }
    }
}
