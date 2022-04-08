package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "gamemode", aliases = { "gm" })
@Permission("eternalcore.command.gamemode")
@UsageMessage("&8» &cPoprawne użycie &7/gamemode <0/1/2/3> <player>")
public class GamemodeCommand {

    private final NoticeService noticeService;

    public GamemodeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(Audience audience, CommandSender sender, @Arg(0) GameMode gameMode, @Arg(1) @Handler(PlayerArgOrSender.class) Player player) {
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
            .audience(audience)
            .send();
    }

}
