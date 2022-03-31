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
        Warp warp = this.warpManager.findWarp(argument).get();

        if (warp == null) {
            Messages messages = userProvider.getUser(invocation)
                    .map(languageManager::getMessages)
                    .orElseGet(languageManager.getDefaultMessages());

            throw new ValidationCommandException(messages.warp().notExist());
        }

        return warp;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return this.warpManager.getWarpMap()
                .keySet()
                .stream()
                .toList();
    }
}

