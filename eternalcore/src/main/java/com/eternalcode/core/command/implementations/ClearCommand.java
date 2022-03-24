package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "clear")
@Permission("eternalcore.command.clear")
public class ClearCommand {

    private final NoticeService noticeService;

    public ClearCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Audience audience, CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        this.clear(player);

        if (sender.equals(player)) {
            return;
        }

        noticeService.notice()
            .message(messages -> messages.other().clearByMessage())
            .placeholder("{PLAYER}", player.getName())
            .audience(audience)
            .send();
    }

    @IgnoreMethod
    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();

        noticeService.notice()
            .message(messages -> messages.other().clearMessage())
            .player(player.getUniqueId())
            .send();
    }

}
