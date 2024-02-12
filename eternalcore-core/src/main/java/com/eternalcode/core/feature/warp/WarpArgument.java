package com.eternalcode.core.feature.warp;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.Optional;

@LiteArgument(type = Warp.class)
class WarpArgument extends AbstractViewerArgument<Warp> {

    private final WarpManager warpManager;

    @Inject
    WarpArgument(WarpManager warpManager, TranslationManager translationManager, ViewerService viewerService) {
        super(viewerService, translationManager);
        this.warpManager = warpManager;
    }

    @Override
    public ParseResult<Warp> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Optional<Warp> warpOption = this.warpManager.findWarp(argument);

        return warpOption.map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(translation.warp().notExist()));

    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Warp> argument, SuggestionContext context) {
        return this.warpManager.getNamesOfWarps().stream()
            .collect(SuggestionResult.collector());
    }

}

