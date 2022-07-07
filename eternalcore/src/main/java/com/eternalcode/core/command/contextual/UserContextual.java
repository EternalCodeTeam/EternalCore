package com.eternalcode.core.command.contextual;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class UserContextual implements Contextual<CommandSender, User> {

    private final LanguageManager languageManager;
    private final UserManager userManager;

    public UserContextual(LanguageManager languageManager, UserManager userManager) {
        this.languageManager = languageManager;
        this.userManager = userManager;
    }

    @Override
    public Result<User, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        if (sender instanceof Player player) {
            return Result.ok(this.userManager.getUser(player.getUniqueId())
                .orThrow(() -> new IllegalStateException("Player " + player.getName() + " is not registered!")));
        }

        Messages messages = this.languageManager.getDefaultMessages();
        String onlyPlayer = messages.argument().onlyPlayer();

        return Result.error(onlyPlayer);
    }

}
