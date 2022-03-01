package com.eternalcode.core.command.message;

import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import org.bukkit.command.CommandSender;
import panda.utilities.text.Joiner;

public class PermissionMessage implements LiteMessage {

    private final LanguageManager languageManager;

    public PermissionMessage(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public String message(MessageInfoContext messageInfoContext) {
        CommandSender sender = (CommandSender) messageInfoContext.getInvocation().sender().getSender();
        this.languageManager.getMessages(sender);

        return this.languageManager.getMessages(sender).argument().permissionMessage().replace("{PERMISSIONS}", Joiner
            .on(", ")
            .join(messageInfoContext.getMissingPermissions())
            .toString());
    }
}
