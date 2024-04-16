package com.eternalcode.core.feature.essentials.tellraw;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "tellraw")
@Permission({ "eternalcore.tellraw" })
class TellRawCommand {

    private final NoticeService noticeService;

    @Inject
    TellRawCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Send a message to the player without any prefixes etc.", arguments = "<player> <notice_type> <message>")
    void tellRaw(@Context Viewer sender, @Arg Player target, @Arg NoticeTextType type, @Join String message) {
        this.noticeService.create()
            .notice(type, message)
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawInfo())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TYPE}", type.name())
            .viewer(sender)
            .send();
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Broadcast a message without any prefixes etc.", arguments = "<notice_type> <message>")
    void tellRawAll(@Context Viewer sender, @Arg NoticeTextType type, @Join String message) {
        this.noticeService.create()
            .notice(type, message)
            .onlinePlayers()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.chat().tellrawAllInfo())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TYPE}", type.name())
            .viewer(sender)
            .send();
    }

}
