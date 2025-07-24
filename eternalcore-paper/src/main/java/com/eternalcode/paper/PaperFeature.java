package com.eternalcode.paper;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class PaperFeature<T> {

    private static final Environment ENVIRONMENT = PaperLib.getEnvironment();
    private static final Logger LOGGER = Logger.getLogger("EternalCore-Paper");

    protected final Consumer<Player> action;
    protected final String featureName;

    protected PaperFeature(Consumer<Player> action, String featureName) {
        this.action = action;
        this.featureName = featureName;
    }

    @SuppressWarnings("deprecation")
    public final void execute(Player player) {
        if (!ENVIRONMENT.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            LOGGER.warning("Feature '" + featureName + "' is only available on Paper or its 1.17.x+ forks");
            return;
        }

        this.action.accept(player);
    }
}
