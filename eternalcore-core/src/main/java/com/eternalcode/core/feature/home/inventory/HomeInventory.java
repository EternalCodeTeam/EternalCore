package com.eternalcode.core.feature.home.inventory;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeMutationService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.home.HomeTeleportService;
import com.eternalcode.core.feature.home.HomesSettings;
import com.eternalcode.core.feature.home.inventory.HomeInventorySlotCalculator.HomeSlot;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.util.ItemStackDisplayUtil;
import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import de.rapha149.signgui.exception.SignGUIVersionException;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Service
public class HomeInventory {

    private static final int FIRST_CONTENT_SLOT = 10;
    private static final int CONTENT_ROW_SIZE = 7;
    private static final int BORDER_ROW_COUNT = 2;
    private static final int MAX_CONTENT_SLOTS = 28; // 4 content rows within Bukkit's 6-row (54-slot) inventory cap, minus the top/bottom border rows

    private final HomeService homeService;
    private final HomeTeleportService homeTeleportService;
    private final HomeMutationService homeMutationService;
    private final HomesSettings homesSettings;
    private final HomeInventoryConfig homeInventoryConfig;
    private final UserManager userManager;
    private final Scheduler scheduler;
    private final MiniMessage miniMessage;
    private final Logger logger;
    private final NoticeService noticeService;

    @Inject
    public HomeInventory(
        HomeService homeService,
        HomeTeleportService homeTeleportService,
        HomeMutationService homeMutationService,
        HomesSettings homesSettings,
        HomeInventoryConfig homeInventoryConfig,
        UserManager userManager,
        Scheduler scheduler,
        MiniMessage miniMessage,
        Logger logger,
        NoticeService noticeService
    ) {
        this.homeService = homeService;
        this.homeTeleportService = homeTeleportService;
        this.homeMutationService = homeMutationService;
        this.homesSettings = homesSettings;
        this.homeInventoryConfig = homeInventoryConfig;
        this.userManager = userManager;
        this.scheduler = scheduler;
        this.miniMessage = miniMessage;
        this.logger = logger;
        this.noticeService = noticeService;
    }

    public void open(Player player) {
        this.scheduler.run(() -> {
            Gui gui = this.create(player);
            gui.open(player);
        });
    }

    private Gui create(Player player) {
        Collection<Home> homes = this.homeService.getHomes(player.getUniqueId());
        int limit = this.homeService.getHomeLimit(player);
        int globalMax = HomeInventorySlotCalculator.globalMaxSlots(this.homesSettings.maxHomes(), this.homesSettings.defaultLimit());

        if (globalMax > MAX_CONTENT_SLOTS) {
            this.logger.log(Level.WARNING, "homes.maxHomes/defaultLimit configuration allows up to " + globalMax
                + " homes, but the home GUI can only display " + MAX_CONTENT_SLOTS + " slots (Bukkit's inventory size limit). "
                + "Showing only the first " + MAX_CONTENT_SLOTS + " slots - consider lowering your permission limits or disabling homes.inventoryEnabled.");
            globalMax = MAX_CONTENT_SLOTS;
        }

        List<HomeSlot> slots = HomeInventorySlotCalculator.calculate(homes, limit, globalMax);

        int rows = ((globalMax + CONTENT_ROW_SIZE - 1) / CONTENT_ROW_SIZE) + BORDER_ROW_COUNT;

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.homeInventoryConfig.display().title()))
            .rows(rows)
            .disableAllInteractions()
            .create();

        gui.getFiller().fillBorder(this.createBorderItem());

        Set<Integer> coordsVisible = new HashSet<>();

        for (int index = 0; index < slots.size(); index++) {
            HomeSlot slot = slots.get(index);
            int guiSlot = FIRST_CONTENT_SLOT + index + ((index / CONTENT_ROW_SIZE) * 2);

            gui.setItem(guiSlot, this.createSlotItem(player, slot, guiSlot, coordsVisible, gui));
        }

        return gui;
    }

    private GuiItem createBorderItem() {
        HomeInventoryConfig.BorderSection border = this.homeInventoryConfig.border();
        ItemStack borderItem = ItemBuilder.from(border.material()).build();

        if (!border.name().isBlank()) {
            Component name = AdventureUtil.resetItalic(this.miniMessage.deserialize(border.name()));
            ItemStackDisplayUtil.applyDisplayName(borderItem, name);
        }

        return new GuiItem(borderItem);
    }

    private GuiItem createSlotItem(Player player, HomeSlot slot, int guiSlot, Set<Integer> coordsVisible, Gui gui) {
        return switch (slot.state()) {
            case OCCUPIED -> this.createOccupiedItem(player, slot.home(), guiSlot, coordsVisible, gui);
            case AVAILABLE -> this.createAvailableItem(player);
            case LOCKED -> this.createLockedItem();
        };
    }

    private GuiItem createOccupiedItem(Player player, Home home, int guiSlot, Set<Integer> coordsVisible, Gui gui) {
        ItemStack itemStack = this.buildOccupiedItemStack(home, coordsVisible.contains(guiSlot));
        GuiItem guiItem = new GuiItem(itemStack);

        guiItem.setAction(event -> {
            ClickType click = event.getClick();

            if (click == ClickType.SHIFT_LEFT) {
                if (!coordsVisible.remove(guiSlot)) {
                    coordsVisible.add(guiSlot);
                }

                gui.updateItem(guiSlot, this.buildOccupiedItemStack(home, coordsVisible.contains(guiSlot)));
                return;
            }

            if (click == ClickType.LEFT) {
                player.closeInventory();
                this.homeTeleportService.teleport(player, home);
                return;
            }

            if (click == ClickType.SHIFT_RIGHT) {
                User user = this.requireUser(player);
                this.homeMutationService.deleteHome(user, home);
                this.open(player);
                return;
            }

            if (click == ClickType.RIGHT) {
                player.closeInventory();
                this.openRenameSign(player, home);
            }
        });

        return guiItem;
    }

    private GuiItem createAvailableItem(Player player) {
        ConfigItem item = this.homeInventoryConfig.availableItem();
        BaseItemBuilder itemBuilder = this.createItem(item);
        GuiItem guiItem = itemBuilder.asGuiItem();

        guiItem.setAction(event -> {
            if (event.getClick() != ClickType.LEFT) {
                return;
            }

            player.closeInventory();
            this.openCreateSign(player);
        });

        return guiItem;
    }

    private GuiItem createLockedItem() {
        ConfigItem item = this.homeInventoryConfig.lockedItem();

        return this.createItem(item).asGuiItem();
    }

    private void openCreateSign(Player player) {
        this.openSign(player, this.homeInventoryConfig.createSignLines(), name -> {
            User user = this.requireUser(player);
            this.homeMutationService.setOrOverrideHome(user, player, name);
            this.open(player);
        });
    }

    private void openRenameSign(Player player, Home home) {
        this.openSign(player, this.homeInventoryConfig.renameSignLines(), newName -> {
            User user = this.requireUser(player);
            this.homeMutationService.renameHome(user, home, newName);
            this.open(player);
        });
    }

    private void openSign(Player player, List<String> lines, Consumer<String> onSubmit) {
        SignGUI signGui;
        try {
            signGui = SignGUI.builder()
                .setLines(lines.toArray(new String[0]))
                .setHandler((editor, result) -> {
                    String name = result.getLineWithoutColor(0).trim();

                    if (name.isEmpty()) {
                        return Collections.emptyList();
                    }

                    return List.of(SignGUIAction.run(() -> this.scheduler.run(() -> onSubmit.accept(name))));
                })
                .build();
        }
        catch (SignGUIVersionException exception) {
            this.logger.log(Level.WARNING, "Failed to open a home sign prompt for " + player.getName(), exception);

            User user = this.requireUser(player);
            this.noticeService.create()
                .user(user)
                .notice(translation -> translation.home().signOpenFailed())
                .send();

            return;
        }

        signGui.open(player);
    }

    private User requireUser(Player player) {
        return this.userManager.getUser(player.getUniqueId())
            .orElseThrow(() -> new IllegalStateException("User not loaded for online player " + player.getUniqueId()));
    }

    private ItemStack buildOccupiedItemStack(Home home, boolean showCoords) {
        ConfigItem item = this.homeInventoryConfig.occupiedItem();

        Component name = AdventureUtil.resetItalic(this.miniMessage.deserialize(item.name().replace("{HOME}", home.getName())));

        List<String> loreLines = new ArrayList<>(item.lore());

        if (showCoords) {
            for (String line : this.homeInventoryConfig.coordsLore()) {
                loreLines.add(line
                    .replace("{WORLD}", home.getLocation().getWorld().getName())
                    .replace("{X}", String.valueOf(home.getLocation().getBlockX()))
                    .replace("{Y}", String.valueOf(home.getLocation().getBlockY()))
                    .replace("{Z}", String.valueOf(home.getLocation().getBlockZ())));
            }
        }

        List<Component> lore = loreLines.stream()
            .map(line -> AdventureUtil.resetItalic(this.miniMessage.deserialize(line)))
            .toList();

        ItemStack itemStack = ItemBuilder.from(item.material()).build();
        return ItemStackDisplayUtil.applyDisplayNameAndLore(itemStack, name, lore);
    }

    private BaseItemBuilder createItem(ConfigItem item) {
        Component name = AdventureUtil.resetItalic(this.miniMessage.deserialize(item.name()));

        List<Component> lore = item.lore().stream()
            .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
            .toList();

        ItemStack itemStack = ItemBuilder.from(item.material()).build();
        ItemStackDisplayUtil.applyDisplayNameAndLore(itemStack, name, lore);

        return ItemBuilder.from(itemStack);
    }
}
