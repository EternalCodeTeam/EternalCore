package com.eteranlcode.containers;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class AdditionalContainerPaper {

    private static final Environment environment = PaperLib.getEnvironment();

    public static void openAdditionalContainer(Player player, Logger logger, @NotNull AdditionalContainerType type) {

        if (!environment.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            logger.warning("This feature is only available on paper, use paper or other paper 1-17-1.19x forks");

            return;
        }

        switch (type) {
            case ANVIL -> player.openAnvil(null, true);
            case STONE_CUTTER -> player.openStonecutter(null, true);
            case CARTOGRAPHY_TABLE -> player.openCartographyTable(null, true);
            case GRINDSTONE -> player.openGrindstone(null, true);
            case LOOM -> player.openLoom(null, true);
            case SMITHING_TABLE -> player.openSmithingTable(null, true);
        }
    }
}
