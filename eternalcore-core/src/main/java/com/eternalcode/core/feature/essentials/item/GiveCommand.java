package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Route(name = "give", aliases = { "i", "item" })
@Permission("eternalcore.give")
public class GiveCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfig;

    public GiveCommand(NoticeService noticeService, PluginConfiguration pluginConfig) {
        this.noticeService = noticeService;
        this.pluginConfig = pluginConfig;
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Gives you an item", arguments = "<item>")
    void execute(Player player, @Arg Material material) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(player, material);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    @DescriptionDocs(description = "Gives an item to another player", arguments = "<item> <player>")
    void execute(Viewer viewer, @Arg Material material, @Arg Player target) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", target.getName())
            .notice(translation -> translation.item().giveGiven())
            .viewer(viewer)
            .send();
    }

    @Execute(required = 2)
    @DescriptionDocs(description = "Gives you an item with a custom amount", arguments = "<item> <amount>")
    void execute(Player player, @Arg Material material, @Arg Integer amount) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(player, material, amount);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 3)
    @DescriptionDocs(description = "Gives an item with a custom amount to another player", arguments = "<item> <amount> <player>")
    void execute(Viewer viewer, @Arg Material material, @Arg Integer amount, @Arg Player target) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(target, material, amount);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", target.getName())
            .notice(translation -> translation.item().giveGiven())
            .viewer(viewer)
            .send();
    }

    private void giveItem(Player player, Material material) {
        int amount = this.pluginConfig.items.defaultGiveAmount;

        if (!material.isItem()) {
            this.noticeService.create()
                .notice(translation -> translation.item().giveNotItem())
                .player(player.getUniqueId())
                .send();
            return;
        }

        ItemStack item = ItemBuilder.from(material)
            .amount(amount)
            .build();

        player.getInventory().addItem(item);
    }

    private void giveItem(Player player, Material material, int amount) {
        ItemStack item = ItemBuilder.from(material)
            .amount(amount)
            .build();

        player.getInventory().addItem(item);
    }

}
