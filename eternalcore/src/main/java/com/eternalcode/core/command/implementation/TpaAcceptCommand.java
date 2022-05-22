package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportRequestService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
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
    public void executeTarget(Player player, @Arg @By("request") Player target) {
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
            .message(messages -> messages.tpa().tpaAcceptReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    @Execute( route = "-all", aliases = "*")
    public void executeAll(Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {

            this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaAcceptNoRequestMessage());

            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {

                this.teleportService.createTeleport(requester.getUniqueId(), requester.getLocation(), player.getLocation(), this.config.otherSettings.tpaTimer);

                this.noticeService
                    .notice()
                    .player(uniqueId)
                    .message(messages -> messages.tpa().tpaAcceptReceivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }

        this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaAcceptAllAccepted());
    }
}
