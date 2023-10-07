package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.RandomUtil;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.List;
import java.util.stream.Collectors;

@Route(name = "teleportorandomplayer", aliases = { "tprp" })
@Permission("eternalcore.tprp")
public class TeleportToRandomPlayerCommand {

    private final Server server;
    private final PluginConfiguration pluginConfiguration;
    private final NoticeService noticeService;

    public TeleportToRandomPlayerCommand(Server server, PluginConfiguration pluginConfiguration, NoticeService noticeService) {
        this.server = server;
        this.pluginConfiguration = pluginConfiguration;
        this.noticeService = noticeService;
    }


    @Execute
    @DescriptionDocs(description = "Teleport to a random player on the server, with the option to filter op players")
    void execute(Player player) {
        List<Player> possibleTargetPlayers = this.server.getOnlinePlayers().stream()
            .filter(target -> this.pluginConfiguration.teleport.includeOpPlayersInRandomTeleport || !target.isOp())
            .collect(Collectors.toList());

        Option<Player> randomPlayerOption = RandomUtil.randomElement(possibleTargetPlayers);

        if (randomPlayerOption.isEmpty()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().noPlayerToRandomTeleportFound())
                .send();
            return;
        }

        Player randomPlayer = randomPlayerOption.get();

        player.teleport(randomPlayer);
        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleport().teleportedToRandomPlayer())
            .placeholder("{PLAYER}", randomPlayer.getName())
            .send();
    }
}
