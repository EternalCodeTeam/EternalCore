package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;

@Route(name = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
@Permission("eternalcore.msg")
public class PrivateChatCommand {

    private final PrivateChatService privateChatService;

    public PrivateChatCommand(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @Execute
    @Min(2)
    void execute(User sender, @Arg User target, @Joiner String message) {
        this.privateChatService.privateMessage(sender, target, message);
    }

}
