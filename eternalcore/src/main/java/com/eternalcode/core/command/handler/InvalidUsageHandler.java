package com.eternalcode.core.command.handler;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.valid.messages.InvalidUseMessage;

public class InvalidUsageHandler implements InvalidUseMessage {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public InvalidUsageHandler(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public String message(LiteInvocation invocation, String useScheme) {
        String permissionMessage = this.userProvider.getUser(invocation)
            .map(this.languageManager::getMessages)
            .orElseGet(this.languageManager.getDefaultMessages())
            .argument().usageMessage();

        return permissionMessage.replace("{USAGE}", useScheme);
    }
}
