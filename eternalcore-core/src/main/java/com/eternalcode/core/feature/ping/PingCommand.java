package com.eternalcode.core.feature.ping;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "ping")
class PingCommand {

    private final NoticeService noticeService;

    @Inject
    PingCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.ping")
    @DescriptionDocs(description = "Shows your ping")
    void execute(@Sender Player sender) {
        this.noticeService.create()
            .notice(translation -> translation.ping().playerPing())
            .placeholder("{PING}", String.valueOf(sender.getPing()))
            .player(sender.getUniqueId())
            .send();
    }


    @Execute
    @Permission("eternalcore.ping.other")
    @DescriptionDocs(description = "Shows ping of other player", arguments = "<player>")
    void execute(@Sender Viewer viewer, @Arg Player target) {
        this.noticeService.create()
            .notice(translation -> translation.ping().targetPlayerPing())
            .placeholder("{PING}", String.valueOf(target.getPing()))
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }

}
