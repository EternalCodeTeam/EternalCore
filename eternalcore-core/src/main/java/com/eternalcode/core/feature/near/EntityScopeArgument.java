package com.eternalcode.core.feature.near;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.List;
import org.bukkit.command.CommandSender;

@LiteArgument(type = EntityScope.class, name = EntityScopeArgument.KEY)
public class EntityScopeArgument extends AbstractViewerArgument<EntityScope> {

    static final String KEY = "entity-scope";

    private static final List<String> SUGGESTIONS = EntityScope.getNames();

    @Inject
    public EntityScopeArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<EntityScope> parse(
        Invocation<CommandSender> invocation,
        String argument,
        Translation translation
    ) {
        try {
            EntityScope entityScope = EntityScope.fromName(argument);
            return ParseResult.success(entityScope);
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.argument().noValidEntityScope());
        }
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<EntityScope> argument,
        SuggestionContext context
    ) {
        return SuggestionResult.of(SUGGESTIONS);
    }
}
