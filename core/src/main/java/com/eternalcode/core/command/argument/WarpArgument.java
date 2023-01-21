package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.warp.Warp;
import com.eternalcode.core.warp.WarpManager;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;

@ArgumentName("warp")
public class WarpArgument extends AbstractViewerArgument<Warp> {

    private final WarpManager warpManager;

    public WarpArgument(WarpManager warpManager, TranslationManager translationManager, BukkitViewerProvider viewerProvider) {
        super(viewerProvider, translationManager);
        this.warpManager = warpManager;
    }

    @Override
    public Result<Warp, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
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

