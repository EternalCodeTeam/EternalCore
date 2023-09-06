package com.eternalcode.core.feature.essentials.tellraw;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "tellraw")
@Permission("eternalcore.tellraw")
public class TellRawCommand {

    private final NoticeService noticeService;

    @Inject
    public TellRawCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Send a message to the player without any prefixes etc.", arguments = "<player> <notice_type> <message>")
    void tellRaw(Viewer sender, @Arg Player target, @Arg NoticeTextType type, @Joiner String message) {
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

    @Execute(route = "-all", aliases = "*")
    @DescriptionDocs(description = "Broadcast a message without any prefixes etc.", arguments = "<notice_type> <message>")
    void tellRawAll(Viewer sender, @Arg NoticeTextType type, @Joiner String message) {
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
