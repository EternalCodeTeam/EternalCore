package com.eternalcode.core.command.message;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Joiner;

public class PermissionMessage implements LiteMessage {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public PermissionMessage(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public String message(MessageInfoContext context) {
        String permissionMessage = userProvider.getUser(context.getInvocation())
            .map(languageManager::getMessages)
            .orElseGet(languageManager.getDefaultMessages())
            .argument().permissionMessage();

        String permissions = Joiner.on(", ")
            .join(context.getMissingPermissions())
            .toString();

        return permissionMessage.replace("{PERMISSIONS}", permissions);
    }
}
