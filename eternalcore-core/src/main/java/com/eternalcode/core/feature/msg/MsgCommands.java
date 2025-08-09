package com.eternalcode.core.feature.msg;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.entity.Player;

@RootCommand
class MsgCommands {

    private final MsgService msgService;
    private final NoticeService noticeService;

    @Inject
    MsgCommands(
        MsgService msgService,
        NoticeService noticeService
    ) {
        this.msgService = msgService;
        this.noticeService = noticeService;
    }

    @Execute(name = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
    @Permission("eternalcore.msg")
    @DescriptionDocs(description = "Send private message to specified player", arguments = "<player> <message>")
    void execute(@Context Player sender, @Arg Player target, @Join String message) {
        this.msgService.sendMessage(sender, target, message);
    }

    @Execute(name = "reply", aliases = { "r" })
    @Permission("eternalcore.reply")
    @DescriptionDocs(description = "Reply to last private message", arguments = "<message>")
    void execute(@Context Player sender, @Join String message) {
        this.msgService.reply(sender, message);
    }

    @Execute(name = "socialspy", aliases = { "spy" })
    @Permission("eternalcore.spy")
    @DescriptionDocs(description = "Enable or disable social spy")
    void socialSpy(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (this.msgService.isSpy(uuid)) {
            this.msgService.disableSpy(uuid);
            this.notifyAboutSocialSpy(uuid);
            return;
        }

        this.msgService.enableSpy(uuid);
        this.notifyAboutSocialSpy(uuid);
    }

    private void notifyAboutSocialSpy(UUID uuid) {
        this.noticeService.create()
            .player(uuid)
            .notice(translation -> this.msgService.isSpy(uuid)
                ? translation.msg().socialSpyEnable()
                : translation.msg().socialSpyDisable())
            .placeholder("{STATE}", translation -> this.msgService.isSpy(uuid)
                ? translation.format().enable()
                : translation.format().disable())
            .send();
    }
}
