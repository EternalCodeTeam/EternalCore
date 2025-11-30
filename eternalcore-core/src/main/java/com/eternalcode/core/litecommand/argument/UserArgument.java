package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

@LiteArgument(type = User.class)
class UserArgument extends AbstractViewerArgument<User> {

    private final Server server;
    private final UserManager userManager;

    @Inject
    UserArgument(TranslationManager translationManager, Server server, UserManager userManager) {
        super(translationManager);
        this.server = server;
        this.userManager = userManager;
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<User> argument, SuggestionContext context) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .collect(SuggestionResult.collector());
    }

    @Override
    public ParseResult<User> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        return this.userManager.getUser(argument)
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(translation.argument().offlinePlayer()));
    }

}
