package com.eternalcode.containers;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class AdditionalContainerPaper {

    private static final Environment ENVIRONMENT = PaperLib.getEnvironment();
    private static final Logger LOGGER = Logger.getLogger("AdditionalContainerPaper");

    @SuppressWarnings("deprecation")
    public static void openAdditionalContainer(Player player, @NotNull AdditionalContainerType container) {
        if (!ENVIRONMENT.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            LOGGER.warning("This feature is only available on paper, use paper or other paper 1-17-1.19x forks");

            return;
        }

        container.open(player);
    }
}
