package com.eternalcode.core.feature.warp;

import com.eternalcode.core.feature.warp.config.WarpConfigItem;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.AdventureUtil;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
class WarpInventory {

    private final TeleportTaskService teleportTaskService;
    private final TranslationManager translationManager;
    private final WarpManager warpManager;
    private final MiniMessage miniMessage;

    @Inject
    WarpInventory(TeleportTaskService teleportTaskService, TranslationManager translationManager, WarpManager warpManager, MiniMessage miniMessage) {
        this.teleportTaskService = teleportTaskService;
        this.translationManager = translationManager;
        this.warpManager = warpManager;
        this.miniMessage = miniMessage;
    }

    private Gui createInventory(Language language) {
        Translation translation = this.translationManager.getMessages(language);
        Translation.WarpSection.WarpInventorySection warpSection = translation.warp().warpInventory();

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(warpSection.title()))
            .rows(warpSection.rows())
            .disableAllInteractions()
            .create();

        warpSection.items().values().forEach(item -> {
            Option<Warp> warpOption = this.warpManager.findWarp(item.warpName());

            if (warpOption.isEmpty()) {
                return;
            }

            Warp warp = warpOption.get();
            WarpConfigItem warpItem = item.warpItem();

            BaseItemBuilder baseItemBuilder = this.createItem(warpItem);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                Player player = (Player) event.getWhoClicked();

                player.closeInventory();

                this.teleportTaskService.createTeleport(
                    player.getUniqueId(),
                    PositionAdapter.convert(player.getLocation()),
                    warp.getPosition(),
                    Duration.ofSeconds(5)
                );
            });

            gui.setItem(warpItem.slot(), guiItem);
        });

        if (warpSection.border().enabled()) {
            Translation.WarpSection.WarpInventorySection.BorderSection borderSection = warpSection.border();

            ItemBuilder borderItem = ItemBuilder.from(borderSection.material());

            if (!borderSection.name().equals("")) {
                borderItem.name(AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(borderSection.name())));
            }

            if (!borderSection.lore().isEmpty()) {
                borderItem.lore(borderSection.lore()
                    .stream()
                    .map(entry -> AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(entry)))
                    .collect(Collectors.toList()));
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

        return gui;
    }

    private BaseItemBuilder createItem(WarpConfigItem warpItem) {
        Component name = AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(warpItem.name()));
        List<Component> lore = warpItem.lore()
            .stream()
            .map(entry -> AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(entry)))
            .toList();

        if (warpItem.material() == Material.PLAYER_HEAD && !warpItem.texture().isEmpty()) {
            return ItemBuilder.skull()
                .name(name)
                .lore(lore)
                .texture(warpItem.texture())
                .glow(warpItem.glow());
        }

        return ItemBuilder.from(warpItem.material())
                .name(name)
                .lore(lore)
                .glow(warpItem.glow());
    }

    public void openInventory(Player player, Language language) {
        this.createInventory(language).open(player);
    }
}
