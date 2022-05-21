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
@Permission("eternalcore.command.gamemode")
public class GamemodeCommand {

    private final NoticeService noticeService;

    public GamemodeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(Viewer audience, CommandSender sender, @Arg GameMode gameMode, @Arg @By("or_sender") Player player) {
        player.setGameMode(gameMode);

        this.noticeService.notice()
            .message(messages -> messages.other().gameModeMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.other().gameModeSetMessage())
            .placeholder("{GAMEMODE}", gameMode.name())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

}
