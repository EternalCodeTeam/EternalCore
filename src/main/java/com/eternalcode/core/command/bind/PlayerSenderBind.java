package com.eternalcode.core.command.bind;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.bind.LiteBind;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ArgumentName("sender")
public class PlayerSenderBind implements LiteBind {

    private final LanguageManager languageManager;

    public PlayerSenderBind(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        CommandSender sender = (CommandSender) invocation.sender().getSender();
        Messages messages = this.languageManager.getMessages(sender);

        if (!(sender instanceof Player)) {
            throw new ValidationCommandException(messages.argument().onlyPlayer());
        }

        return invocation.sender();
    }
}
