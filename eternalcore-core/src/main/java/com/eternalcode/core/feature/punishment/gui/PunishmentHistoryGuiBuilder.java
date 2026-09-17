package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.date.DateFormatter;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import xyz.xenondevs.invui.gui.Markers;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.BoundItem;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
class PunishmentHistoryGuiBuilder {

    private final PunishmentSettings punishmentSettings;
    private final DateFormatter dateFormatter;
    private final NoticeService noticeService;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;
    private final Logger logger;

    @Inject
    PunishmentHistoryGuiBuilder(
        PunishmentSettings punishmentSettings,
        DateFormatter dateFormatter,
        NoticeService noticeService, MiniMessage miniMessage,
        Scheduler scheduler,
        Logger logger
    ) {
        this.punishmentSettings = punishmentSettings;
        this.dateFormatter = dateFormatter;
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
        this.logger = logger;
    }

    void open(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session) {
        session.loadNextBatch()
            .thenAccept(none -> this.scheduler.run(() -> this.openWindow(viewer, titleTemplate, session)))
            .exceptionally(throwable -> {
                this.logger.log(Level.SEVERE, "Failed to load punishment history for GUI", throwable);

                this.scheduler.run(() -> this.noticeService.create()
                    .notice(translation -> translation.punishment().historyError())
                    .sender(viewer)
                    .send());

                return null;
            });
    }

    private void openWindow(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session) {
        AtomicReference<Window> windowRef = new AtomicReference<>();
        String[] structure = PunishmentHistoryGuiLayout.buildStructure(this.punishmentSettings.historyGuiPageSize());

        BoundItem back = BoundItem.pagedBuilder()
            .setItemProvider((player, gui) -> {
                if (gui.getPage() > 0) {
                    return new ItemBuilder(this.punishmentSettings.historyGuiBackArrowMaterial())
                        .setName(this.punishmentSettings.historyGuiBackArrowName());
                }

                return new ItemBuilder(this.punishmentSettings.historyGuiFillerMaterial()).hideTooltip(true);
            })
            .addClickHandler((item, gui, click) -> {
                gui.setPage(gui.getPage() - 1);
                this.updateTitle(windowRef.get(), titleTemplate, (PagedGui<Item>) gui, session);
            })
            .build();

        BoundItem forward = BoundItem.pagedBuilder()
            .setItemProvider((player, gui) -> {
                boolean canGoForward = gui.getPage() < gui.getPageCount() - 1 || session.hasMore();

                if (canGoForward) {
                    return new ItemBuilder(this.punishmentSettings.historyGuiForwardArrowMaterial())
                        .setName(this.punishmentSettings.historyGuiForwardArrowName());
                }

                return new ItemBuilder(this.punishmentSettings.historyGuiFillerMaterial()).hideTooltip(true);
            })
            .addClickHandler((item, gui, click) -> this.advance(session, (PagedGui<Item>) gui, windowRef, titleTemplate))
            .build();

        PagedGui<Item> gui = PagedGui.itemsBuilder()
            .setStructure(structure)
            .addIngredient('#', Item.simple(new ItemBuilder(this.punishmentSettings.historyGuiFillerMaterial()).hideTooltip(true)))
            .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
            .addIngredient('<', back)
            .addIngredient('>', forward)
            .setContent(this.toItems(session))
            .build();

        Window window = Window.builder()
            .setTitle(this.renderTitle(titleTemplate, gui, session))
            .setUpperGui(gui)
            .setViewer(viewer)
            .build();

        windowRef.set(window);
        window.open();
    }

    private void advance(PunishmentHistoryGuiSession session, PagedGui<Item> gui, AtomicReference<Window> windowRef, String titleTemplate) {
        boolean onLastLoadedPage = gui.getPage() >= gui.getPageCount() - 1;

        if (!onLastLoadedPage) {
            gui.setPage(gui.getPage() + 1);
            this.updateTitle(windowRef.get(), titleTemplate, gui, session);
            return;
        }

        if (!session.hasMore()) {
            return;
        }

        session.loadNextBatch().thenAccept(none -> this.scheduler.run(() -> {
            gui.setContent(this.toItems(session));
            gui.setPage(gui.getPage() + 1);
            this.updateTitle(windowRef.get(), titleTemplate, gui, session);
        }));
    }

    private void updateTitle(Window window, String titleTemplate, PagedGui<Item> gui, PunishmentHistoryGuiSession session) {
        if (window == null) {
            return;
        }

        window.setTitle(this.renderTitle(titleTemplate, gui, session));
        window.updateTitle();
    }

    private String renderTitle(String titleTemplate, PagedGui<Item> gui, PunishmentHistoryGuiSession session) {
        String pagesLabel = session.hasMore() ? (gui.getPageCount() + "+") : String.valueOf(gui.getPageCount());

        return titleTemplate
            .replace("{PAGE}", String.valueOf(gui.getPage() + 1))
            .replace("{PAGES}", pagesLabel);
    }

    private List<Item> toItems(PunishmentHistoryGuiSession session) {
        return session.loadedEntries().stream()
            .map(entry -> PunishmentHistoryItemFactory.create(entry, this.dateFormatter, this.miniMessage, this.punishmentSettings))
            .toList();
    }
}
