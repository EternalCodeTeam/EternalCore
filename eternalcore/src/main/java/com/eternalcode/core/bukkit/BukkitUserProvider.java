package com.eternalcode.core.bukkit;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import org.bukkit.entity.Player;
import panda.std.Option;

@Deprecated
public class BukkitUserProvider {

    private final UserManager userManager;

    @Deprecated
    public BukkitUserProvider(UserManager userManager) {
        this.userManager = userManager;
    }

    @Deprecated
    public Option<User> getUser(Player player) {
        return this.userManager.getUser(player.getUniqueId());
    }

    @Deprecated
    public Option<User> getUser(LiteInvocation invocation) {
        if (invocation.sender().getHandle() instanceof Player player) {
            return this.getUser(player);
        }

        return Option.none();
    }

}
