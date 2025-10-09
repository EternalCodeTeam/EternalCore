package com.eternalcode.core.feature.gamemode;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageException;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "gamemode", aliases = "gm")
class GameModeCommand {

    private final CommandConfiguration commandConfiguration;
    private final NoticeService noticeService;

    @Inject
    GameModeCommand(CommandConfiguration commandConfiguration, NoticeService noticeService) {
        this.commandConfiguration = commandConfiguration;
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.gamemode")
    void executeAlias(@Context Invocation<CommandSender> invocation, @Sender Player player) {
        GameMode gameMode = this.commandConfiguration.getGameMode(invocation.label());

        if (gameMode == null) {
            throw new InvalidUsageException(InvalidUsage.Cause.INVALID_ARGUMENT);
        }

        this.execute(player, gameMode);
    }

    @Execute
    @Permission("eternalcore.gamemode")
    @DescriptionDocs(description = "Sets your gamemode", arguments = "<gamemode>")
    void execute(@Sender Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.noticeService.create()
            .notice(translation -> translation.gamemode().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.gamemode.other")
    @DescriptionDocs(description = "Sets gamemode of another player", arguments = "<gamemode> <player>")
    void execute(@Sender Viewer sender, @Arg GameMode gameMode, @Arg Player player) {
        player.setGameMode(gameMode);

        this.noticeService.create()
            .notice(translation -> translation.gamemode().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.gamemode().gameModeSetMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .placeholder("{PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }

}
