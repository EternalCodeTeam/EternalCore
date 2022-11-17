package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "reply", aliases = { "r" })
@Permission("eternalcore.reply")
public class PrivateChatReplyCommand {

    private final PrivateChatService privateChatService;

    public PrivateChatReplyCommand(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @Execute
    @Min(1)
    void execute(User sender, @Joiner String message) {
        this.privateChatService.reply(sender, message);
    }

}
