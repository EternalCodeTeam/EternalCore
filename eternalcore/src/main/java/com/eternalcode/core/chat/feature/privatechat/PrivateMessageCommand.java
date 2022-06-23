package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;

@Section(route = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
@Permission("eternalcore.msg")
public class PrivateMessageCommand {

    private final PrivateChatService privateChatService;

    public PrivateMessageCommand(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @Execute
    @Min(2)
    public void execute(User sender, @Arg User target, @Joiner String message) {
        this.privateChatService.privateMessage(sender, target, message);
    }

}
