package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.command.god")
public class GodCommand {

    private final NoticeService noticeService;

    public GodCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(CommandSender sender, Audience audience, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.noticeService
            .notice()
            .placeholder("{STATE}", messages -> player.isInvulnerable() ? messages.format().formatEnable() : messages.format().formatDisable())
            .message(messages -> messages.other().godMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .notice()
            .placeholder("{STATE}", messages -> player.isInvulnerable() ? messages.format().formatEnable() : messages.format().formatDisable())
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().godSetMessage())
            .audience(audience)
            .send();
    }
}
