package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.notification.NotificationType;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class MessageActionArgument implements SingleArgumentHandler<NotificationType> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public MessageActionArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }


    @Override
    public NotificationType parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(IllegalArgumentException.class, () -> NotificationType.valueOf(argument.toUpperCase()))
            .orThrow(() -> {
                Messages messages = userProvider.getUser(invocation)
                    .map(languageManager::getMessages)
                    .orElseGet(languageManager.getDefaultMessages());

                return new ValidationCommandException(messages.argument().noArgument());
            });
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(NotificationType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .toList();
    }
}
