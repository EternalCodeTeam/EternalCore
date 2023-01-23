package com.eternalcode.core.feature.essentials.item;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.MaterialUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.amount.Between;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Route(name = "give", aliases = { "i", "item" })
@Permission("eternalcore.give")
public class GiveCommand {

    private final NoticeService noticeService;

    public GiveCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Between(min = 1, max = 2)
    void execute(Viewer audience, CommandSender sender, @Arg @Name("material") Material material, @Arg @By("or_sender") Player player) {
        String formattedMaterial = MaterialUtil.format(material);

        this.giveItem(player, material);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .notice(translation -> translation.item().giveReceived())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", player.getName())
            .notice(translation -> translation.item().giveGiven())
            .viewer(audience)
            .send();
    }

    private void giveItem(Player player, Material material) {
        int amount = 64;

        if (material.isItem()) {
            amount = 1;
        }

        ItemStack item = ItemBuilder.from(material)
            .amount(amount)
            .build();

        player.getInventory().addItem(item);
    }

}
