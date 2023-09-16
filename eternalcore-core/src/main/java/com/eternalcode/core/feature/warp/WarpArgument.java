package com.eternalcode.core.feature.warp;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;

@LiteArgument(type = Warp.class)
@ArgumentName("warp")
class WarpArgument extends AbstractViewerArgument<Warp> {

    private final WarpManager warpManager;

    @Inject
    WarpArgument(WarpManager warpManager, TranslationManager translationManager, ViewerProvider viewerProvider) {
        super(viewerProvider, translationManager);
        this.warpManager = warpManager;
    }

    @Override
    public Result<Warp, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        Option<Warp> warpOption = this.warpManager.findWarp(argument);

        if (warpOption.isEmpty()) {
            return Result.error(translation.warp().notExist());
        }

        return Result.ok(warpOption.get());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.warpManager.getNamesOfWarps().stream()
            .map(Suggestion::of)
            .toList();
    }

}

