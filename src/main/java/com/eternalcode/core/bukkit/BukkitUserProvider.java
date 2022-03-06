package com.eternalcode.core.bukkit;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.LiteInvocation;
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
        return userManager.getUser(player.getUniqueId());
    }

    public Option<User> getUser(CommandSender sender) {
        if (sender instanceof Player player) {
            return this.getUser(player);
        }

        return Option.none();
    }

    public Option<User> getUser(LiteInvocation invocation) {
        if (invocation.sender().getSender() instanceof Player player) {
            return this.getUser(player);
        }

        return Option.none();
    }

}
