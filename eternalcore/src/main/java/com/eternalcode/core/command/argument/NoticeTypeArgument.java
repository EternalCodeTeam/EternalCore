package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class NoticeTypeArgument implements OneArgument<NoticeType> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public NoticeTypeArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(NoticeType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<NoticeType, ?> parse(LiteInvocation invocation, String argument) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> NoticeType.valueOf(argument.toUpperCase()))
            .toResult(() -> {
                Messages messages = this.userProvider.getUser(invocation)
                    .map(this.languageManager::getMessages)
                    .orElseGet(this.languageManager.getDefaultMessages());

                return messages.argument().noArgument();
            });
    }
}
