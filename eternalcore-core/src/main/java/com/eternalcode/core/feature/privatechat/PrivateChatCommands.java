package com.eternalcode.core.feature.privatechat;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.root.RootRoute;
import org.bukkit.entity.Player;

import java.util.UUID;

@RootRoute
public class PrivateChatCommands {

    private final PrivateChatService privateChatService;
    private final NoticeService noticeService;

    public PrivateChatCommands(PrivateChatService privateChatService, NoticeService noticeService) {
        this.privateChatService = privateChatService;
        this.noticeService = noticeService;
    }

    @Execute(route = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
    @Min(2)
    @Permission("eternalcore.msg")
    @DocsDescription(description = "Send private message to specified player", arguments = "<player> <message>")
    void execute(User sender, @Arg User target, @Joiner String message) {
        this.privateChatService.privateMessage(sender, target, message);
    }

    @Execute(route = "reply", aliases = { "r" })
    @Min(1)
    @Permission("eternalcore.reply")
    @DocsDescription(description = "Reply to last private message", arguments = "<message>")
    void execute(User sender, @Joiner String message) {
        this.privateChatService.reply(sender, message);
    }

    @Execute(route = "socialspy", aliases = { "spy" })
    @Permission("eternalcore.spy")
    @DocsDescription(description = "Enable or disable social spy")
    void socialSpy(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.privateChatService.isSpy(uuid)) {
            this.privateChatService.disableSpy(uuid);
            this.noticeService.player(uuid, translation -> translation.privateChat().socialSpyDisable());
            return;
        }

        this.noticeService.player(uuid, translation -> translation.privateChat().socialSpyEnable());
        this.privateChatService.enableSpy(uuid);
    }

}
