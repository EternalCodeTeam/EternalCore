package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArg;
import com.eternalcode.core.teleport.TeleportRequestManager;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Section(route = "tpa")
@Permission("eternalcore.command.tpa")
@UsageMessage("&8» &cPoprawne użycie &7tpa <player>")
public class TpaCommand {

    private final TeleportRequestManager teleportRequestManager;
    private final NoticeService noticeService;
    private final Server server;

    public TpaCommand(TeleportRequestManager teleportRequestManager, NoticeService noticeService, Server server) {
        this.teleportRequestManager = teleportRequestManager;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    @MinArgs(1)
    public void execute(User player, @Arg(0) @Handler(PlayerArg.class) Player target) {

        if (teleportRequestManager.contains(player.getUniqueId())) {
            this.noticeService.notice()
                .message(messages -> messages.teleport().teleportRequestAlreadySent())
                .player(player.getUniqueId())
                .send();
            return;
        }
        this.noticeService
            .notice()
            .message(messages -> messages.teleport().teleportRequestSendMessage())
            .placeholder("{TARGET}", target.getName())
            .player(player.getUniqueId())
            .send();
        this.noticeService
            .notice()
            .message(messages -> messages.teleport().teleportRequestReceivedMessage())
            .placeholder("{SENDER}", player.getName())
            .player(target.getUniqueId())
            .send();

        teleportRequestManager.addTeleportRequest(target.getUniqueId(), player.getUniqueId());
    }
}
