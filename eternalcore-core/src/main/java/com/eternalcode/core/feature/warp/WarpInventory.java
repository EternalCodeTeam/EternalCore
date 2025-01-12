package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.Translation.WarpSection.WarpInventorySection;
import com.eternalcode.core.translation.TranslationManager;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class WarpInventory {

    private static final int GUI_ITEM_SLOT_WITH_TOP_BORDER = 9;
    private static final int GUI_ITEM_SLOT_WITHOUT_BORDER = 0;
    private static final int GUI_ITEM_SLOT_WITH_ALL_BORDER = 1;
    private static final int GUI_ITEM_SLOT_WITH_BORDER = 10;

    private static final int GUI_ROW_SIZE_WITHOUT_BORDER = 9;
    private static final int GUI_ROW_SIZE_WITH_BORDER = 7;

    private final TranslationManager translationManager;
    private final WarpService warpManager;
    private final Server server;
    private final MiniMessage miniMessage;
    private final WarpTeleportService warpTeleportService;
    private final ConfigurationManager configurationManager;
    private final PluginConfiguration config;

    @Inject
    WarpInventory(
        TranslationManager translationManager,
        WarpService warpManager,
        Server server,
        MiniMessage miniMessage,
        WarpTeleportService warpTeleportService,
        ConfigurationManager configurationManager,
        PluginConfiguration config
    ) {
        this.translationManager = translationManager;
        this.warpManager = warpManager;
        this.server = server;
        this.miniMessage = miniMessage;
        this.warpTeleportService = warpTeleportService;
        this.configurationManager = configurationManager;
        this.config = config;
    }

    private Gui createInventory(Language language) {
        Translation translation = this.translationManager.getMessages(language);
        Translation.WarpSection.WarpInventorySection warpSection = translation.warp().warpInventory();

        int rowsCount;
        int size = warpSection.items().size();

        if (!warpSection.border().enabled()) {
            rowsCount = (size + 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 1;
        }
        else {
            switch (warpSection.border().fillType()) {
                case BORDER, ALL -> rowsCount = (size + 1) / GUI_ROW_SIZE_WITH_BORDER + 3;
                case TOP, BOTTOM -> rowsCount = (size + 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 2;
                default -> throw new IllegalStateException("Unexpected value: " + warpSection.border().fillType());
            }
        }

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(warpSection.title()))
            .rows(rowsCount)
            .disableAllInteractions()
            .create();

        this.createWarpItems(warpSection, gui);
        this.createBorder(warpSection, gui);
        this.createDecorations(warpSection, gui);

        return gui;
    }

    private void createBorder(WarpInventorySection warpSection, Gui gui) {
        if (warpSection.border().enabled()) {
            WarpInventorySection.BorderSection borderSection = warpSection.border();

            ItemBuilder borderItem = ItemBuilder.from(borderSection.material());

            if (!borderSection.name().isBlank()) {
                borderItem.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(borderSection.name())));
            }

            if (!borderSection.lore().isEmpty()) {
                borderItem.lore(borderSection.lore()
                    .stream()
                    .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
                    .toList());
            }

            GuiItem guiItem = new GuiItem(borderItem.build());

            switch (borderSection.fillType()) {
                case BORDER -> gui.getFiller().fillBorder(guiItem);
                case ALL -> gui.getFiller().fill(guiItem);
                case TOP -> gui.getFiller().fillTop(guiItem);
                case BOTTOM -> gui.getFiller().fillBottom(guiItem);
                default -> throw new IllegalStateException("Unexpected value: " + borderSection.fillType());
            }
        }
    }

    private void createDecorations(WarpInventorySection warpSection, Gui gui) {
        for (ConfigItem item : warpSection.decorationItems().items()) {
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

    private void createWarpItems(WarpInventorySection warpSection, Gui gui) {
        warpSection.items().values().forEach(item -> {
            Optional<Warp> warpOptional = this.warpManager.findWarp(item.warpName());

            if (warpOptional.isEmpty()) {
                return;
            }

            Warp warp = warpOptional.get();
            ConfigItem warpItem = item.warpItem();

            BaseItemBuilder baseItemBuilder = this.createItem(warpItem);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                Player player = (Player) event.getWhoClicked();

                player.closeInventory();
                this.warpTeleportService.teleport(player, warp);
            });

            gui.setItem(warpItem.slot(), guiItem);
        });
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

    public void openInventory(Player player, Language language) {
        this.createInventory(language).open(player);
    }

    public void addWarp(Warp warp) {
        if (!this.warpManager.isExist(warp.getName())) {
            return;
        }

        for (Language language : this.translationManager.getAvailableLanguages()) {
            AbstractTranslation translation = (AbstractTranslation) this.translationManager.getMessages(language);
            Translation.WarpSection.WarpInventorySection warpSection = translation.warp().warpInventory();

            int size = warpSection.items().size();
            int slot;

            if (!warpSection.border().enabled()) {
                slot = GUI_ITEM_SLOT_WITHOUT_BORDER + size;
            }
            else {
                switch (warpSection.border().fillType()) {
                    case BORDER -> slot = GUI_ITEM_SLOT_WITH_BORDER + size + ((size / GUI_ROW_SIZE_WITH_BORDER) * 2);
                    case ALL -> slot = GUI_ITEM_SLOT_WITH_ALL_BORDER + size + ((size / GUI_ROW_SIZE_WITH_BORDER) * 2);
                    case TOP -> slot = GUI_ITEM_SLOT_WITH_TOP_BORDER + size;
                    case BOTTOM -> slot = size;
                    default -> throw new IllegalStateException("Unexpected value: " + warpSection.border().fillType());
                }
            }


            warpSection.addItem(warp.getName(),
                WarpInventoryItem.builder()
                    .withWarpName(warp.getName())
                    .withWarpItem(ConfigItem.builder()
                        .withName(this.config.warp.itemNamePrefix + warp.getName())
                        .withLore(Collections.singletonList(this.config.warp.itemLore))
                        .withMaterial(this.config.warp.itemMaterial)
                        .withTexture(this.config.warp.itemTexture)
                        .withSlot(slot)
                        .withGlow(true)
                        .build())
                    .build());

            this.configurationManager.save(translation);

        }
    }

    public boolean removeWarp(String warpName) {

        if (!this.warpManager.isExist(warpName)) {
            return false;
        }

        for (Language language : this.translationManager.getAvailableLanguages()) {

            AbstractTranslation translation = (AbstractTranslation) this.translationManager.getMessages(language);
            Translation.WarpSection.WarpInventorySection warpSection = translation.warp().warpInventory();

            warpSection.removeItem(warpName);

            this.configurationManager.save(translation);

        }

        return true;
    }
}
