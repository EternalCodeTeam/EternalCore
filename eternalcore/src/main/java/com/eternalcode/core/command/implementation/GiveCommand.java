package com.eternalcode.core.command.implementation;

import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Between;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "give", aliases = {"i", "item"})
@Permission("eternalcore.give")
public class GiveCommand {

    private final NoticeService noticeService;

    public GiveCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(Viewer audience, CommandSender sender, @Arg Material material, @Arg @By("or_sender") Player player) {
        String formattedMaterial = material.name().replaceAll("_", " "); // TODO: Add formatter to Material

        this.giveItem(player, material);

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .message(messages -> messages.other().giveReceived())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().giveGiven())
            .viewer(audience)
            .send();
    }

    private void giveItem(Player player, Material material) {
        int amount = 64;

        if (material.isItem()) {
            amount = 1;
        }

        ItemStack item = new ItemBuilder(material)
            .amount(amount)
            .build();

        player.getInventory().addItem(item);
    }

}
