package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgument;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "kill")
@Permission("eternalcore.command.kill")
public class KillCommand {

    private final NoticeService noticeService;

    public KillCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Audience audience, @Arg(0) @Handler(PlayerArgument.class) Player player) {
        player.setHealth(0);

        this.noticeService.notice()
            .message(messages -> messages.other().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .audience(audience)
            .send();
    }

}
