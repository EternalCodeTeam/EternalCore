package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.feature.warp.messages.WarpMessages;
import com.eternalcode.core.feature.warp.messages.WarpMessages.WarpInventorySection;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import static com.eternalcode.core.util.FutureHandler.whenSuccess;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    private final TranslationManager translationManager;
    private final LanguageService languageService;
    private final WarpService warpService;
    private final Server server;
    private final MiniMessage miniMessage;
    private final WarpTeleportService warpTeleportService;
    private final ConfigurationManager configurationManager;
    private final PluginConfiguration config;
    private final Scheduler scheduler;

    @Inject
    WarpInventory(
        TranslationManager translationManager,
        LanguageService languageService,
        WarpService warpService,
        Server server,
        MiniMessage miniMessage,
        WarpTeleportService warpTeleportService,
        ConfigurationManager configurationManager,
        PluginConfiguration config,
        Scheduler scheduler
    ) {
        this.translationManager = translationManager;
        this.languageService = languageService;
        this.warpService = warpService;
        this.server = server;
        this.miniMessage = miniMessage;
        this.warpTeleportService = warpTeleportService;
        this.configurationManager = configurationManager;
        this.config = config;
        this.scheduler = scheduler;
    }

    public void openInventory(Player player) {
        this.languageService.getLanguage(player.getUniqueId())
            .thenApply(language -> this.createInventory(player, language))
            .whenComplete(whenSuccess(gui -> this.scheduler.run(() -> gui.open(player))));
    }

    private Gui createInventory(Player player, Language language) {
        Translation translation = this.translationManager.getMessages(language);
        WarpMessages.WarpInventorySection warpSection = translation.warp().warpInventory();

        int rowsCount;
        int size = warpSection.items().size();

        if (!warpSection.border().enabled()) {
            rowsCount = (size + 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 1;
        }
        else {
            switch (warpSection.border().fillType()) {
                case BORDER, ALL -> rowsCount = (size - 1) / GUI_ROW_SIZE_WITH_BORDER + 1 + BORDER_ROW_COUNT;
                case TOP, BOTTOM -> rowsCount = (size - 1) / GUI_ROW_SIZE_WITHOUT_BORDER + 1 + UGLY_BORDER_ROW_COUNT;
                default -> throw new IllegalStateException("Unexpected value: " + warpSection.border().fillType());
            }
        }

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(warpSection.title()))
            .rows(rowsCount)
            .disableAllInteractions()
            .create();

        this.createWarpItems(player, warpSection, gui);
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

    private void createWarpItems(Player player, WarpInventorySection warpSection, Gui gui) {
        warpSection.items().values().forEach(item -> {
            Optional<Warp> warpOptional = this.warpService.findWarp(item.warpName());

            if (warpOptional.isEmpty()) {
                return;
            }

            Warp warp = warpOptional.get();
            ConfigItem warpItem = item.warpItem();

            if (!warp.hasPermissions(player)) {
                return;
            }

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

    public void addWarp(Warp warp) {
        if (!this.warpService.exists(warp.getName())) {
            return;
        }

        for (Language language : this.translationManager.getAvailableLanguages()) {
            AbstractTranslation translation = (AbstractTranslation) this.translationManager.getMessages(language);
            WarpMessages.WarpInventorySection warpSection = translation.warp().warpInventory();
            int slot = getSlot(warpSection);

            warpSection.addItem(
                warp.getName(),
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

    private int getSlot(WarpMessages.WarpInventorySection warpSection) {
        int size = warpSection.items().size();
        if (!warpSection.border().enabled()) {
            return GUI_ITEM_SLOT_WITHOUT_BORDER + size;
        }

        return switch (warpSection.border().fillType()) {
            case BORDER -> GUI_ITEM_SLOT_WITH_BORDER + size + ((size / WarpInventory.GUI_ROW_SIZE_WITH_BORDER) * 2);
            case ALL -> GUI_ITEM_SLOT_WITH_ALL_BORDER + size + ((size / WarpInventory.GUI_ROW_SIZE_WITH_BORDER) * 2);
            case TOP -> GUI_ITEM_SLOT_WITH_TOP_BORDER + size;
            case BOTTOM -> size;
        };
    }

    public void removeWarp(String warpName) {
        if (!this.config.warp.autoAddNewWarps) {
            return;
        }

        for (Language language : this.translationManager.getAvailableLanguages()) {
            AbstractTranslation translation = (AbstractTranslation) this.translationManager.getMessages(language);
            WarpMessages.WarpInventorySection warpSection = translation.warp().warpInventory();
            WarpInventoryItem removed = warpSection.removeItem(warpName);

            if (removed != null) {
                this.shiftWarpItems(removed, warpSection);
            }

            this.configurationManager.save(translation);
        }
    }

    private void shiftWarpItems(WarpInventoryItem removed, WarpMessages.WarpInventorySection warpSection) {
        int removedSlot = removed.warpItem.slot;
        List<WarpInventoryItem> itemsToShift = warpSection.items().values().stream()
            .filter(item -> item.warpItem.slot > removedSlot)
            .sorted(Comparator.comparingInt(item -> item.warpItem.slot))
            .toList();

        int currentShift = removedSlot;
        for (WarpInventoryItem item : itemsToShift) {
            int nextShift = item.warpItem.slot;
            item.warpItem.slot = currentShift;
            currentShift = nextShift;
        }
    }
}
