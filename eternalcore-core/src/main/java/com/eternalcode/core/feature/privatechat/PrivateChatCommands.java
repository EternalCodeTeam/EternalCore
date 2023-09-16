package com.eternalcode.core.feature.privatechat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.root.RootRoute;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

import java.util.UUID;

@RootRoute
class PrivateChatCommands {

    private final PrivateChatService privateChatService;
    private final NoticeService noticeService;

    @Inject
    PrivateChatCommands(PrivateChatService privateChatService, NoticeService noticeService) {
        this.privateChatService = privateChatService;
        this.noticeService = noticeService;
    }

    @Execute(route = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
    @Min(2)
    @Permission("eternalcore.msg")
    @DescriptionDocs(description = "Send private message to specified player", arguments = "<player> <message>")
    void execute(User sender, @Arg User target, @Joiner String message) {
        this.privateChatService.privateMessage(sender, target, message);
    }

    @Execute(route = "reply", aliases = { "r" })
    @Min(1)
    @Permission("eternalcore.reply")
    @DescriptionDocs(description = "Reply to last private message", arguments = "<message>")
    void execute(User sender, @Joiner String message) {
        this.privateChatService.reply(sender, message);
    }

    @Execute(route = "socialspy", aliases = { "spy" })
    @Permission("eternalcore.spy")
    @DescriptionDocs(description = "Enable or disable social spy")
    void socialSpy(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpy(uuid)) {
            this.modifySocialSpy(uuid, this.privateChatService::disableSpy);
            return;
        }

        this.modifySocialSpy(uuid, this.privateChatService::enableSpy);
    }

    private void modifySocialSpy(UUID uuid, Consumer<UUID> featureModifier) {
        featureModifier.accept(uuid);

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
