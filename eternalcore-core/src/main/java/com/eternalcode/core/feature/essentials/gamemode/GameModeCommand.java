package com.eternalcode.core.feature.essentials.gamemode;

import com.eternalcode.annotations.scan.command.Description;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Route(name = "gamemode", aliases = { "gm" })
@Permission("eternalcore.gamemode")
public class GameModeCommand {

    private final NoticeService noticeService;

    public GameModeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    @Description(description = "Sets your gamemode", arguments = "<gamemode>")
    void execute(Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.noticeService.create()
            .notice(translation -> translation.player().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    @Description(description = "Sets gamemode of another player", arguments = "<gamemode> <player>")
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
