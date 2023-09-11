package com.eternalcode.core.feature.essentials.gamemode;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.handle.LiteException;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Route(name = "gamemode", aliases = { "gm" })
@Permission("eternalcore.gamemode")
class GameModeCommand {

    private final CommandConfiguration commandConfiguration;
    private final NoticeService noticeService;

    @Inject
    GameModeCommand(CommandConfiguration commandConfiguration, NoticeService noticeService) {
        this.commandConfiguration = commandConfiguration;
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    void executeAlias(LiteInvocation invocation, Player player) {
        GameMode gameMode = this.commandConfiguration.getGameMode(invocation.label());

        if (gameMode == null) {
            throw LiteException.newInvalidUsage();
        }

        this.execute(player, gameMode);
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Sets your gamemode", arguments = "<gamemode>")
    void execute(Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.noticeService.create()
            .notice(translation -> translation.player().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    @DescriptionDocs(description = "Sets gamemode of another player", arguments = "<gamemode> <player>")
    void execute(Viewer sender, @Arg GameMode gameMode, @Arg Player player) {
        player.setGameMode(gameMode);

        this.noticeService.create()
            .notice(translation -> translation.player().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.player().gameModeSetMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .placeholder("{PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }

}
