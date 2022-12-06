package com.eternalcode.core.command.argument;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.warp.Warp;
import com.eternalcode.core.warp.WarpManager;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;

@ArgumentName("warp")
public class WarpArgument extends AbstractViewerArgument<Warp> {

    private final WarpManager warpManager;

    public WarpArgument(WarpManager warpManager, LanguageManager languageManager, BukkitViewerProvider viewerProvider) {
        super(viewerProvider, languageManager);
        this.warpManager = warpManager;
    }

    @Override
    public Result<Warp, String> parse(LiteInvocation invocation, String argument, Messages messages) {
        Option<Warp> warpOption = this.warpManager.findWarp(argument);

        if (warpOption.isEmpty()) {
            return Result.error(messages.warp().notExist());
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

