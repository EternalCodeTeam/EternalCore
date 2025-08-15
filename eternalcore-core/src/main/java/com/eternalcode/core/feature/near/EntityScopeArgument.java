package com.eternalcode.core.feature.near;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

import java.util.List;

@LiteArgument(type = String.class, name = EntityScopeArgument.KEY)
public class EntityScopeArgument extends AbstractViewerArgument<EntityScope> {

    private static final List<String> SUGGESTIONS = EntityScope.getNames();
    static final String KEY = "entity-scope";

    @Inject
    public EntityScopeArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<EntityScope> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {

        try {
            EntityScope entityScope = EntityScope.fromName(argument);
            return ParseResult.success(entityScope);
        } catch (IllegalArgumentException e) {
            return ParseResult.failure(translation.argument().noValidEntityScope());
        }

    }

}
