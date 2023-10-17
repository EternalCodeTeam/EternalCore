package com.eternalcode.core.feature.privatechat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import org.bukkit.entity.Player;

import java.util.UUID;

@RootCommand
class PrivateChatCommands {

    private final PrivateChatService privateChatService;
    private final NoticeService noticeService;

    @Inject
    PrivateChatCommands(PrivateChatService privateChatService, NoticeService noticeService) {
        this.privateChatService = privateChatService;
        this.noticeService = noticeService;
    }

    @Execute(name = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
    @Permission("eternalcore.msg")
    @DescriptionDocs(description = "Send private message to specified player", arguments = "<player> <message>")
    void execute(@Context User sender, @Arg User target, @Join String message) {
        this.privateChatService.privateMessage(sender, target, message);
    }

    @Execute(name = "reply", aliases = { "r" })
    @Permission("eternalcore.reply")
    @DescriptionDocs(description = "Reply to last private message", arguments = "<message>")
    void execute(@Context User sender, @Join String message) {
        this.privateChatService.reply(sender, message);
    }

    @Execute(name = "socialspy", aliases = { "spy" })
    @Permission("eternalcore.spy")
    @DescriptionDocs(description = "Enable or disable social spy")
    void socialSpy(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpy(uuid)) {
            this.privateChatService.disableSpy(uuid);
            this.notifyAboutSocialSpy(uuid);
            return;
        }

        this.privateChatService.enableSpy(uuid);
        this.notifyAboutSocialSpy(uuid);
    }

    private void notifyAboutSocialSpy(UUID uuid) {
        this.noticeService.create()
            .player(uuid)
            .notice(translation -> this.privateChatService.isSpy(uuid)
                ? translation.privateChat().socialSpyEnable()
                : translation.privateChat().socialSpyDisable())
            .placeholder("{STATE}", translation -> this.privateChatService.isSpy(uuid)
                ? translation.format().enable()
                : translation.format().disable())
            .send();
    }

}
