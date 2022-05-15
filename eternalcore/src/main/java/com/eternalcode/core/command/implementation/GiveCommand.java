package com.eternalcode.core.command.implementation;

import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "give", aliases = {"i", "item"})
@Permission("eternalcore.command.give")
public class GiveCommand {

    private final NoticeService noticeService;

    public GiveCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(Audience audience, CommandSender sender, @Arg(0) Material material, @Arg(1) @Handler(PlayerArgOrSender.class) Player player) {
        String formattedMaterial = material.name().replaceAll("_", " "); // TODO: Add formatter to Material

        this.giveItem(player, material);

        this.noticeService.notice()
            .placeholder("{ITEM}", formattedMaterial)
            .message(messages -> messages.other().giveReceived())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .placeholder("{ITEM}", formattedMaterial)
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().giveGiven())
            .audience(audience)
            .send();
    }

    @IgnoreMethod
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
