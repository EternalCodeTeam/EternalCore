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

@Route(name = "teleport-to-random-player", aliases = {"tprp"})
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
    @DescriptionDocs(description = "Teleport to random player on server, with filter op players option")
    void execute(Player player) {
        List<Player> possibleTargetPlayers = this.server.getOnlinePlayers().stream()
            .filter(target -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !target.isOp())
            .collect(Collectors.toList());

        if (possibleTargetPlayers.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().noPlayerToRandomTeleportFound())
                .send();
            return;
        }

        Player randomPlayer = possibleTargetPlayers.get(RANDOM.nextInt(possibleTargetPlayers.size()));

        player.teleport(randomPlayer);
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleport().teleportedToRandomPlayer())
            .placeholder("{PLAYER}", randomPlayer.getName())
            .send();
    }
}
