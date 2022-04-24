package com.eternalcode.core.command.bind;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.Parameter;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.entity.Player;

public class UserBind implements Parameter {

    private final LanguageManager languageManager;
    private final UserManager userManager;

    public UserBind(LanguageManager languageManager, UserManager userManager) {
        this.languageManager = languageManager;
        this.userManager = userManager;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        if (invocation.sender().getSender() instanceof Player player) {
            return this.userManager.getUser(player.getUniqueId())
                .orThrow(() -> new IllegalStateException("Player " + player.getName() + " is not registered!"));
        }

        Messages messages = this.languageManager.getDefaultMessages();
        String onlyPlayer = messages.argument().onlyPlayer();

        throw new ValidationCommandException(onlyPlayer);
    }

}
