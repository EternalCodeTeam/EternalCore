package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
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
    private final BukkitUserProvider userProvider;

    public WarpArgument(WarpManager warpManager, LanguageManager languageManager, BukkitUserProvider userProvider) {
        this.warpManager = warpManager;
        this.languageManager = languageManager;
        this.userProvider = userProvider;
    }

    @Override
    public Result<Warp, String> parse(LiteInvocation invocation, String argument) {
        Option<Warp> warpOption = this.warpManager.findWarp(argument);

        if (warpOption.isEmpty()) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            return Result.error(messages.warp().notExist());
        }

        return Result.ok(warpOption.get());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.warpManager.getWarpMap().keySet().stream()
            .map(Suggestion::of)
            .toList();
    }

}

