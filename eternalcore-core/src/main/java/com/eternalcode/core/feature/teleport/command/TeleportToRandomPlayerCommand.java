package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Route(name = "teleport-to-random-player", aliases = { "tprp" })
@Permission("eternalcore.tprp")
public class TeleportToRandomPlayerCommand {

    private static final Random RANDOM = new Random();

    private final Server server;
    private final PluginConfiguration pluginConfiguration;
    private final NoticeService noticeService;

    public TeleportToRandomPlayerCommand(Server server, PluginConfiguration pluginConfiguration, NoticeService noticeService) {
        this.server = server;
        this.pluginConfiguration = pluginConfiguration;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport to random player on server")
    void execute(Player player) {
        List<Player> applicablePlayers = this.server.getOnlinePlayers().stream()
            .filter(players -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !players.isOp())
            .collect(Collectors.toList());

        if (applicablePlayers.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().noPlayerToRandomTeleportFound())
                .send();
            return;
        }

        Player target = applicablePlayers.get(RANDOM.nextInt(applicablePlayers.size()));

        player.teleport(target.getLocation());
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleport().teleportedToRandomPlayer())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }
}
