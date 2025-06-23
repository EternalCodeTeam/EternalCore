package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.feature.warp.repository.WarpConfig;
import com.eternalcode.core.feature.warp.repository.WarpConfig.WarpGuiSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import static com.eternalcode.core.util.FutureHandler.whenSuccess;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.Comparator;
import java.util.List;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

    private final LanguageService languageService;
    private final WarpService warpService;
    private final Server server;
    private final MiniMessage miniMessage;
    private final WarpTeleportService warpTeleportService;
    private final Scheduler scheduler;
    private final WarpConfig warpConfig;

    @Inject
    WarpInventory(
        LanguageService languageService,
        WarpService warpService,
        Server server,
        MiniMessage miniMessage,
        WarpTeleportService warpTeleportService,
        Scheduler scheduler,
        WarpConfig warpConfig
    ) {
        this.languageService = languageService;
        this.warpService = warpService;
        this.server = server;
        this.miniMessage = miniMessage;
        this.warpTeleportService = warpTeleportService;
        this.scheduler = scheduler;
        this.warpConfig = warpConfig;
    }

    public void openInventory(Player player) {
        this.languageService.getLanguage(player.getUniqueId())
            .thenApply(language -> this.createInventory(player, language))
            .whenComplete(whenSuccess(gui -> this.scheduler.run(() -> gui.open(player))));
    }

    private Gui createInventory(Player player, Language language) {
        WarpConfig.WarpGuiSettings guiSettings = this.warpConfig.guiSettings;

        int rowsCount = createrBorder(guiSettings);

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(guiSettings.title))
            .rows(rowsCount)
            .disableAllInteractions()
            .create();

        this.createWarpItems(player, gui);
        this.createBorder(guiSettings, gui);
        this.createDecorations(guiSettings, gui);

        return gui;
    }

    private int createrBorder(WarpGuiSettings guiSettings) {
        int rowsCount;
        int size = this.warpService.getWarps().size();

        if (!guiSettings.borderEnabled) {
            rowsCount = (size + 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 1;
        }
        else {
            switch (guiSettings.borderFillType) {
                case BORDER, ALL -> rowsCount = (size - 1) / GUI_ROW_SIZE_WITH_BORDER + 1 + BORDER_ROW_COUNT;
                case TOP, BOTTOM -> rowsCount = (size - 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 1 + UGLY_BORDER_ROW_COUNT;
                default -> throw new IllegalStateException("Unexpected value: " + guiSettings.borderFillType);
            }
        }
        return rowsCount;
    }

    private void createBorder(WarpConfig.WarpGuiSettings guiSettings, Gui gui) {
        if (guiSettings.borderEnabled) {
            ItemBuilder borderItem = ItemBuilder.from(guiSettings.borderMaterial);

            if (!guiSettings.borderName.isBlank()) {
                borderItem.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(guiSettings.borderName)));
            }

            if (!guiSettings.borderLore.isEmpty()) {
                borderItem.lore(guiSettings.borderLore
                    .stream()
                    .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
                    .toList());
            }

            GuiItem guiItem = new GuiItem(borderItem.build());

            switch (guiSettings.borderFillType) {
                case BORDER -> gui.getFiller().fillBorder(guiItem);
                case ALL -> gui.getFiller().fill(guiItem);
                case TOP -> gui.getFiller().fillTop(guiItem);
                case BOTTOM -> gui.getFiller().fillBottom(guiItem);
                default -> throw new IllegalStateException("Unexpected value: " + guiSettings.borderFillType);
            }
        }
    }

    private void createDecorations(WarpConfig.WarpGuiSettings guiSettings, Gui gui) {
        for (ConfigItem item : guiSettings.decorationItems) {
            BaseItemBuilder baseItemBuilder = this.createItem(item);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                Player player = (Player) event.getWhoClicked();

                if (item.commands.isEmpty()) {
                    return;
                }

                for (String command : item.commands) {
                    this.server.dispatchCommand(player, command);
                }

                player.closeInventory();
            });

            gui.setItem(item.slot(), guiItem);
        }
    }

    private void createWarpItems(Player player, Gui gui) {
        List<Warp> warps = this.warpService.getWarps().stream()
            .filter(warp -> warp.hasPermissions(player))
            .sorted(Comparator.comparing(Warp::getName))
            .toList();

        int slot = getStartingSlot();
        
        for (Warp warp : warps) {
            WarpConfig.WarpConfigEntry warpEntry = this.warpConfig.warps.get(warp.getName());
            if (warpEntry == null) continue;
            
            WarpConfig.WarpGuiItem guiItem = warpEntry.guiItem;
            
            ConfigItem configItem = ConfigItem.builder()
                .withName(guiItem.name.replace("{WARP_NAME}", warp.getName()))
                .withLore(guiItem.lore)
                .withMaterial(guiItem.material)
                .withTexture(guiItem.texture)
                .withSlot(slot++)
                .withGlow(guiItem.glow)
                .build();
            
            BaseItemBuilder baseItemBuilder = this.createItem(configItem);
            GuiItem item = baseItemBuilder.asGuiItem();

            item.setAction(event -> {
                if (!warp.hasPermissions(player)) {
                    return;
                }

                player.closeInventory();
                this.warpTeleportService.teleport(player, warp);
            });

            gui.setItem(configItem.slot(), item);
        }
    }

    private int getStartingSlot() {
        WarpConfig.WarpGuiSettings guiSettings = this.warpConfig.guiSettings;
        
        if (!guiSettings.borderEnabled) {
            return GUI_ITEM_SLOT_WITHOUT_BORDER;
        }
        
        return switch (guiSettings.borderFillType) {
            case TOP -> GUI_ITEM_SLOT_WITH_TOP_BORDER;
            case ALL -> GUI_ITEM_SLOT_WITH_ALL_BORDER;
            case BORDER, BOTTOM -> GUI_ITEM_SLOT_WITH_BORDER;
        };
    }

    private BaseItemBuilder createItem(ConfigItem item) {
        BaseItemBuilder baseItemBuilder = ItemBuilder.from(item.material());

        if (item.texture() != null && !item.texture().isBlank()) {
            baseItemBuilder = ItemBuilder.skull()
                .texture(item.texture());
        }

        if (item.name() != null && !item.name().isBlank()) {
            baseItemBuilder.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(item.name())));
        }

        if (item.lore() != null && !item.lore().isEmpty()) {
            baseItemBuilder.lore(item.lore()
                .stream()
                .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
                .toList());
        }

        if (item.glow()) {
            baseItemBuilder.glow();
        }

        return baseItemBuilder;
    }
}
