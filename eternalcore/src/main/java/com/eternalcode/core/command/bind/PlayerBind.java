package com.eternalcode.core.command.bind;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.Parameter;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.entity.Player;

public class PlayerBind implements Parameter {

    private final LanguageManager languageManager;

    public PlayerBind(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        if (invocation.sender().getSender() instanceof Player player) {
            return player;
        }

        Messages messages = this.languageManager.getDefaultMessages();
        String onlyPlayer = messages.argument().onlyPlayer();

        throw new ValidationCommandException(onlyPlayer);
    }

}
