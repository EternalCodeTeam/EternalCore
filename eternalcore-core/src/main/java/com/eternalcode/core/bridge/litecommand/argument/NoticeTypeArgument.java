package com.eternalcode.core.bridge.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@LiteArgument(type = NoticeTextType.class)
class NoticeTypeArgument extends AbstractViewerArgument<NoticeTextType> {

    @Inject
    NoticeTypeArgument(ViewerService viewerService, TranslationManager translationManager) {
        super(viewerService, translationManager);
    }

    @Override
    public ParseResult<NoticeTextType> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            return ParseResult.success(NoticeTextType.valueOf(argument.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.argument().noArgument());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<NoticeTextType> argument, SuggestionContext context) {
        return Arrays.stream(NoticeTextType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .collect(SuggestionResult.collector());
    }

}
