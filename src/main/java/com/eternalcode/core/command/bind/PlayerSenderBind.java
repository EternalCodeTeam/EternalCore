package com.eternalcode.core.command.bind;

import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.bind.LiteBind;
import dev.rollczi.litecommands.platform.LiteSender;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.entity.Player;

@ArgumentName("sender")
public class PlayerSenderBind implements LiteBind {

    private final LanguageManager languageManager;

    public PlayerSenderBind(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        LiteSender sender = invocation.sender();

        if (!(sender.getSender() instanceof Player)) {
            Messages defaultMessages = this.languageManager.getDefaultMessages();

            throw new ValidationCommandException(defaultMessages.argument().onlyPlayer());
        }

        return sender.getSender();
    }
}
