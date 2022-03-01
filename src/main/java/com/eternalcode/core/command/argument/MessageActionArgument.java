package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.NotificationType;
import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class MessageActionArgument implements SingleArgumentHandler<NotificationType> {

    private final LanguageManager languageManager;

    public MessageActionArgument(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }


    @Override
    public NotificationType parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        CommandSender sender = (CommandSender) invocation.sender().getSender();
        Messages messages = this.languageManager.getMessages(sender);

        return Option.attempt(IllegalArgumentException.class, () -> NotificationType.valueOf(argument.toUpperCase()))
            .orThrow(() -> new ValidationCommandException(messages.argument().noArgument()));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(NotificationType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .toList();
    }
}
