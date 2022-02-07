package com.eternalcode.core.command.binds;

import com.eternalcode.core.configuration.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.bind.LiteBind;
import dev.rollczi.litecommands.platform.LiteSender;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.entity.Player;

@ArgumentName("sender")
public class PlayerSenderBind implements LiteBind {

    private final MessagesConfiguration messages;

    public PlayerSenderBind(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        LiteSender sender = invocation.sender();

        if (!(sender.getSender() instanceof Player)) {
            throw new ValidationCommandException(ValidationInfo.CUSTOM, messages.messagesSection.onlyPlayer);
        }

        return sender.getSender();
    }
}
