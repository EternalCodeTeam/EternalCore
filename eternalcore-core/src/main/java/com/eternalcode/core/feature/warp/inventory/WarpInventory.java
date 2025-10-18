package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.feature.warp.WarpService;
import com.eternalcode.core.feature.warp.WarpSettings;
import com.eternalcode.core.feature.warp.WarpTeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Service
public class WarpInventory {

    private static final int GUI_ITEM_SLOT_WITH_TOP_BORDER = 9;
    private static final int GUI_ITEM_SLOT_WITHOUT_BORDER = 0;
    private static final int GUI_ITEM_SLOT_WITH_ALL_BORDER = 1;
    private static final int GUI_ITEM_SLOT_WITH_BORDER = 10;

    private static final int GUI_ROW_SIZE_WITHOUT_BORDER = 9;
    private static final int GUI_ROW_SIZE_WITH_BORDER = 7;

    private static final int BORDER_ROW_COUNT = 2;
    private static final int UGLY_BORDER_ROW_COUNT = 1;

    private final WarpService warpService;
    private final Server server;
    private final MiniMessage miniMessage;
    private final WarpTeleportService warpTeleportService;
    private final WarpSettings warpSettings;
    private final Scheduler scheduler;
    private final WarpInventoryConfigService warpInventoryConfigService;

    @Inject
    public WarpInventory(
        WarpService warpService,
        Server server,
        MiniMessage miniMessage,
        WarpTeleportService warpTeleportService,
        WarpSettings warpSettings,
        Scheduler scheduler,
        WarpInventoryConfigService warpInventoryConfigService
    ) {
        this.warpService = warpService;
        this.server = server;
        this.miniMessage = miniMessage;
        this.warpTeleportService = warpTeleportService;
        this.warpSettings = warpSettings;
        this.scheduler = scheduler;
        this.warpInventoryConfigService = warpInventoryConfigService;
    }

    public void open(Player player) {
        this.warpInventoryConfigService.getWarpInventoryData()
            .thenAccept(warpData -> {
                this.scheduler.run(() -> {
                    Gui gui = this.create(player, warpData);
                    gui.open(player);
                });
            })
            .exceptionally(FutureHandler::handleException);
    }

    private Gui create(Player player, WarpInventoryConfigService.WarpInventoryConfigData warpData) {
        int rows = calculateRowsCount(warpData);

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(warpData.title()))
            .rows(rows)
            .disableAllInteractions()
            .create();

        this.createWarpItems(player, warpData, gui);
        this.createBorder(warpData, gui);
        this.createDecorations(warpData, gui);

        return gui;
    }

    private int calculateRowsCount(WarpInventoryConfigService.WarpInventoryConfigData warpData) {
        int size = warpData.items().size();

        if (!warpData.border().enabled()) {
            return (size + GUI_ROW_SIZE_WITHOUT_BORDER - 1) / GUI_ROW_SIZE_WITHOUT_BORDER;
        }

        return switch (warpData.border().fillType()) {
            case BORDER, ALL -> (size + GUI_ROW_SIZE_WITH_BORDER - 1) / GUI_ROW_SIZE_WITH_BORDER + BORDER_ROW_COUNT;
            case TOP, BOTTOM -> (size + GUI_ROW_SIZE_WITHOUT_BORDER - 1) / GUI_ROW_SIZE_WITHOUT_BORDER + UGLY_BORDER_ROW_COUNT;
        };
    }

    private void createBorder(WarpInventoryConfigService.WarpInventoryConfigData warpData, Gui gui) {
        if (!warpData.border().enabled()) {
            return;
        }

        WarpInventoryConfig.BorderSection borderSection = warpData.border();
        GuiItem guiItem = createBorderItem(borderSection);

        switch (borderSection.fillType()) {
            case BORDER -> gui.getFiller().fillBorder(guiItem);
            case ALL -> gui.getFiller().fill(guiItem);
            case TOP -> gui.getFiller().fillTop(guiItem);
            case BOTTOM -> gui.getFiller().fillBottom(guiItem);
        }
    }

    private GuiItem createBorderItem(WarpInventoryConfig.BorderSection borderSection) {
        ItemBuilder borderItem = ItemBuilder.from(borderSection.material());

        if (!borderSection.name().isBlank()) {
            borderItem.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(borderSection.name())));
        }

        if (!borderSection.lore().isEmpty()) {
            List<Component> loreComponents = borderSection.lore()
                .stream()
                .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
                .toList();
            borderItem.lore(loreComponents);
        }

        return new GuiItem(borderItem.build());
    }

    private void createDecorations(WarpInventoryConfigService.WarpInventoryConfigData warpData, Gui gui) {
        for (ConfigItem item : warpData.decorationItems().items()) {
            BaseItemBuilder baseItemBuilder = this.createItem(item);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                Player player = (Player) event.getWhoClicked();
                this.executeDecorationCommands(player, item);
            });

            gui.setItem(item.slot(), guiItem);
        }
    }

    private void executeDecorationCommands(Player player, ConfigItem item) {
        if (item.commands.isEmpty()) {
            return;
        }

        for (String command : item.commands) {
            this.server.dispatchCommand(player, command);
        }
        player.closeInventory();
    }

    private void createWarpItems(Player player, WarpInventoryConfigService.WarpInventoryConfigData warpData, Gui gui) {
        warpData.items().values().forEach(item -> {
            Optional<Warp> warpOptional = this.warpService.findWarp(item.warpName());

            if (warpOptional.isEmpty()) {
                return;
            }

            Warp warp = warpOptional.get();

            if (!warp.hasPermissions(player)) {
                return;
            }

            this.createWarpGuiItem(player, warp, item.warpItem(), gui);
        });
    }

    private void createWarpGuiItem(Player player, Warp warp, ConfigItem warpItem, Gui gui) {
        BaseItemBuilder baseItemBuilder = this.createItem(warpItem);
        GuiItem guiItem = baseItemBuilder.asGuiItem();

        guiItem.setAction(event -> {
            if (!warp.hasPermissions(player)) {
                return;
            }

            player.closeInventory();
            this.warpTeleportService.teleport(player, warp);
        });

        gui.setItem(warpItem.slot(), guiItem);
    }

    private BaseItemBuilder createItem(ConfigItem item) {
        Component name = AdventureUtil.resetItalic(this.miniMessage.deserialize(item.name()));

        List<Component> lore = item.lore()
            .stream()
            .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
            .toList();

        if (item.material() == Material.PLAYER_HEAD && !item.texture().isEmpty()) {
            return ItemBuilder.skull()
                .name(name)
                .lore(lore)
                .texture(item.texture())
                .glow(item.glow());
        }

        return ItemBuilder.from(item.material())
            .name(name)
            .lore(lore)
            .glow(item.glow());
    }

    public CompletableFuture<Void> addWarp(Warp warp) {
        if (!this.warpService.exists(warp.getName())) {
            return CompletableFuture.completedFuture(null);
        }

        return this.warpInventoryConfigService.getWarpInventoryData()
            .thenCompose(warpData -> {
                int slot = getSlot(warpData);

                WarpInventoryItem warpInventoryItem = createWarpInventoryItem(warp, slot);

                return this.warpInventoryConfigService.addWarpItem(warp.getName(), warpInventoryItem);
            })
            .exceptionally(FutureHandler::handleException);
    }

    private WarpInventoryItem createWarpInventoryItem(Warp warp, int slot) {
        return WarpInventoryItem.builder()
            .withWarpName(warp.getName())
            .withWarpItem(ConfigItem.builder()
                .withName(this.warpSettings.itemNamePrefix() + warp.getName())
                .withLore(Collections.singletonList(this.warpSettings.itemLore()))
                .withMaterial(this.warpSettings.itemMaterial())
                .withTexture(this.warpSettings.itemTexture())
                .withSlot(slot)
                .withGlow(true)
                .build())
            .build();
    }

    private int getSlot(WarpInventoryConfigService.WarpInventoryConfigData warpData) {
        int size = warpData.items().size();
        if (!warpData.border().enabled()) {
            return GUI_ITEM_SLOT_WITHOUT_BORDER + size;
        }

        return switch (warpData.border().fillType()) {
            case BORDER -> GUI_ITEM_SLOT_WITH_BORDER + size + ((size / GUI_ROW_SIZE_WITH_BORDER) * 2);
            case ALL -> GUI_ITEM_SLOT_WITH_ALL_BORDER + size + ((size / GUI_ROW_SIZE_WITH_BORDER) * 2);
            case TOP -> GUI_ITEM_SLOT_WITH_TOP_BORDER + size;
            case BOTTOM -> size;
        };
    }

    public CompletableFuture<Void> removeWarp(String warpName) {
        return this.warpInventoryConfigService.getWarpItems()
            .thenCompose(items -> {
                if (!items.containsKey(warpName)) {
                    return CompletableFuture.completedFuture(null);
                }

                return this.warpInventoryConfigService.removeWarpItem(warpName)
                    .thenCompose(removed -> {
                        if (removed != null) {
                            return this.shiftWarpItems(removed, items);
                        }
                        return CompletableFuture.completedFuture(null);
                    });
            })
            .exceptionally(FutureHandler::handleException);
    }

    private CompletableFuture<Void> shiftWarpItems(WarpInventoryItem removed, Map<String, WarpInventoryItem> items) {
        int removedSlot = removed.warpItem.slot;

        List<WarpInventoryItem> itemsToShift = items.values().stream()
            .filter(item -> item.warpItem.slot > removedSlot)
            .sorted(Comparator.comparingInt(item -> item.warpItem.slot))
            .toList();

        int currentShift = removedSlot;
        for (WarpInventoryItem item : itemsToShift) {
            int nextShift = item.warpItem.slot;
            item.warpItem.slot = currentShift;
            currentShift = nextShift;
        }

        return CompletableFuture.completedFuture(null);
    }
}
