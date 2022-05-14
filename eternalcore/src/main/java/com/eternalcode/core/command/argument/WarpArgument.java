package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.List;

@ArgumentName("warp")
public class WarpArgument implements SingleArgumentHandler<Warp> {

    private final WarpManager warpManager;
    private final LanguageManager languageManager;
    private final BukkitUserProvider userProvider;

    public WarpArgument(WarpManager warpManager, LanguageManager languageManager, BukkitUserProvider userProvider) {
        this.warpManager = warpManager;
        this.languageManager = languageManager;
        this.userProvider = userProvider;
    }

    @Override
    public Warp parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Option<Warp> warpOption = this.warpManager.findWarp(argument);

        if (warpOption.isEmpty()) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            throw new ValidationCommandException(messages.warp().notExist());
        }

        return warpOption.get();
    }

    @Override
    public List<String> tabulation(LiteInvocation invocation, String command, String[] args) {
        return this.warpManager.getWarpMap()
                .keySet()
                .stream()
                .toList();
    }
}

