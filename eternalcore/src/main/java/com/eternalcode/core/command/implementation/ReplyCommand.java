package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.PrivateChatService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "reply", aliases = { "r" })
@Permission("eternalcore.reply")
public class ReplyCommand {

    private final PrivateChatService privateChatService;

    public ReplyCommand(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @Execute
    @Min(1)
    public void execute(Player sender, @Joiner String message) {
        this.privateChatService.reply(sender, message);
    }

}
