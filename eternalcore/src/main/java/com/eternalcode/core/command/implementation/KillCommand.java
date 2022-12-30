package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Route(name = "kill")
@Permission("eternalcore.kill")
public class KillCommand {

    private final NoticeService noticeService;

    public KillCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Viewer audience, @Arg Player player) {
        player.setHealth(0);

        this.noticeService.create()
            .notice(messages -> messages.player().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

}
