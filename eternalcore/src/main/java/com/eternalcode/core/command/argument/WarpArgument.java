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
public class WarpArgument implements OneArgument<Warp> {

    private final WarpManager warpManager;
    private final LanguageManager languageManager;
    private final BukkitViewerProvider viewerProvider;

    public WarpArgument(WarpManager warpManager, LanguageManager languageManager, BukkitViewerProvider viewerProvider) {
        this.warpManager = warpManager;
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
    }

    @Override
    public Result<Warp, String> parse(LiteInvocation invocation, String argument) {
        Option<Warp> warpOption = this.warpManager.findWarp(argument);

        if (warpOption.isEmpty()) {
            Viewer viewer = this.viewerProvider.any(invocation);
            Messages messages = this.languageManager.getMessages(viewer);

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

