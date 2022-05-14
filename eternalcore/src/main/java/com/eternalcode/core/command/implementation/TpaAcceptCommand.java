package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.RequesterArgument;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportRequestService;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Section(route = "tpaaccept", aliases = "tpaccept")
@Permission("eternalcore.command.tpaccept")
public class TpaAcceptCommand {

    private final TeleportRequestService requestService;
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public TpaAcceptCommand(TeleportRequestService requestService, TeleportService teleportService, NoticeService noticeService, PluginConfiguration config, Server server) {
        this.requestService = requestService;
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;
    }

    @Execute
    @Required(1)
    public void executeTarget(Player player, @Arg(0) @Handler(RequesterArgument.class) Player target) {
        this.teleportService.createTeleport(target.getUniqueId(), target.getLocation(), player.getLocation(), this.config.otherSettings.tpaTimer);

        this.requestService.removeRequest(target.getUniqueId());

        this.noticeService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.tpa().tpaAcceptMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService
            .notice()
            .player(target.getUniqueId())
            .message(messages -> messages.tpa().tpaAcceptRecivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    @Execute( route = "-all", aliases = "*")
    public void executeAll(Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {

            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.tpa().tpaAcceptNoRequestMessageAll())
                .send();

            return;
        }

        requests.forEach(uniqueId -> {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {

                this.teleportService.createTeleport(requester.getUniqueId(), requester.getLocation(), player.getLocation(), this.config.otherSettings.tpaTimer);

                this.noticeService
                    .notice()
                    .player(uniqueId)
                    .message(messages -> messages.tpa().tpaAcceptRecivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        });

        this.noticeService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.tpa().tpaAcceptAllAccepted())
            .send();
    }
}
