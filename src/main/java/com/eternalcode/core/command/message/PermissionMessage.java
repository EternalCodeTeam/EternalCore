package com.eternalcode.core.command.message;

import dev.rollczi.litecommands.valid.messages.LiteMessage;
import dev.rollczi.litecommands.valid.messages.MessageInfoContext;
import panda.utilities.text.Joiner;

public class PermissionMessage implements LiteMessage {

    private final MessagesConfiguration messages;

    public PermissionMessage(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public String message(MessageInfoContext messageInfoContext) {
        String permissions = Joiner
            .on(", ")
            .join(messageInfoContext.getMissingPermissions())
            .toString();

        return messages.argumentSection.permissionMessage.replace("{PERMISSIONS}", permissions);
    }

}
