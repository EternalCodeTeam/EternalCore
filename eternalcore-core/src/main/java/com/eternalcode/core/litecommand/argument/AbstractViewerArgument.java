package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractViewerArgument<T> extends ArgumentResolver<CommandSender, T> {

    protected final TranslationManager translationManager;

    protected AbstractViewerArgument(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Override
    protected ParseResult<T> parse(Invocation<CommandSender> invocation, Argument<T> context, String argument) {
        if (invocation.sender() instanceof Player player) {
            Translation translation = this.translationManager.getMessages(player.getUniqueId());
            return this.parse(invocation, argument, translation);
        }

        Translation translation = this.translationManager.getMessages();
        return this.parse(invocation, argument, translation);
    }

    public abstract ParseResult<T> parse(Invocation<CommandSender> invocation, String argument, Translation translation);
}
