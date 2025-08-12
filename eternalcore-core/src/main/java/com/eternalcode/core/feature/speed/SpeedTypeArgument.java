package com.eternalcode.core.feature.speed;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@LiteArgument(type = SpeedType.class)
class SpeedTypeArgument extends AbstractViewerArgument<SpeedType> {

    @Inject
    SpeedTypeArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<SpeedType> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            return ParseResult.success(SpeedType.valueOf(argument.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.player().speedTypeNotCorrect());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<SpeedType> argument, SuggestionContext context) {
        return Arrays.stream(SpeedType.values())
            .map(SpeedType::name)
            .collect(SuggestionResult.collector());
    }

}
