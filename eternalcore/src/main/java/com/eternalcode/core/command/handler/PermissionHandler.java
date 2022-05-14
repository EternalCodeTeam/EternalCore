package com.eternalcode.core.command.handler;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import dev.rollczi.litecommands.valid.messages.PermissionMessage;
import panda.utilities.text.Joiner;

import java.util.List;

public class PermissionHandler implements PermissionMessage {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public PermissionHandler(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public String message(LiteInvocation invocation, List<String> permissions) {
        String permissionMessage = this.userProvider.getUser(invocation)
            .map(this.languageManager::getMessages)
            .orElseGet(this.languageManager.getDefaultMessages())
            .argument().permissionMessage();

        String perms = Joiner.on(", ")
            .join(permissions)
            .toString();

        return permissionMessage.replace("{PERMISSIONS}", perms);
    }
}
