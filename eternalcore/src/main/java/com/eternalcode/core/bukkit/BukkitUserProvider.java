package com.eternalcode.core.bukkit;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Deprecated
public class BukkitUserProvider {

    private final UserManager userManager;

    public BukkitUserProvider(UserManager userManager) {
        this.userManager = userManager;
    }

    public Option<User> getUser(Player player) {
        return this.userManager.getUser(player.getUniqueId());
    }

    public Option<User> getUser(CommandSender sender) {
        if (sender instanceof Player player) {
            return this.getUser(player);
        }

        return Option.none();
    }

    public Option<User> getUser(LiteInvocation invocation) {
        if (invocation.sender().getHandle() instanceof Player player) {
            return this.getUser(player);
        }

        return Option.none();
    }

    public Audience getAudience(LiteInvocation invocation) {
        Option<User> userOption = getUser(invocation);

        if (userOption.isEmpty()) {
            return Audience.console();
        }

        User user = userOption.get();

        return Audience.player(user.getUniqueId(), user.getSettings().getLanguage());
    }


}
