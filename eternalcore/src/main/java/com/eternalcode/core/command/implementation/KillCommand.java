package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "kill")
@Permission("eternalcore.kill")
public class KillCommand {

    private final NoticeService noticeService;

    public KillCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Viewer audience, @Arg Player player) {
        player.setHealth(0);

        this.noticeService.create()
            .message(messages -> messages.other().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

}
