package com.eternalcode.core.privatechat;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "msg", aliases = { "message", "m", "whisper", "tell", "t" })
@Permission("eternalcore.msg")
public class PrivateMessageCommand {

    private final PrivateChatService privateChatService;

    public PrivateMessageCommand(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @Execute
    @Min(2)
    public void execute(Player sender, @Arg Player target, @Joiner String message) {
        this.privateChatService.sendMessage(sender, target, message);
    }

}
