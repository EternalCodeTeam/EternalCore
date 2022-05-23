package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Between;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "gamemode", aliases = { "gm" })
@Permission("eternalcore.gamemode")
public class GamemodeCommand {

    private final NoticeService noticeService;

    public GamemodeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    public void execute(Player sender, @Arg GameMode gameMode) {
        sender.setGameMode(gameMode);

        this.noticeService.notice()
            .message(messages -> messages.other().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(required = 2)
    public void execute(Viewer sender, @Arg GameMode gameMode, @Arg Player player) {
        player.setGameMode(gameMode);

        this.noticeService.notice()
            .message(messages -> messages.other().gameModeSetMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .placeholder("{PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }

}
