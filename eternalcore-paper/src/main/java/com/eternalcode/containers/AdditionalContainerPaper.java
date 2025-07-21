package com.eternalcode.containers;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class AdditionalContainerPaper {

    public static final AdditionalContainerPaper ANVIL = new AdditionalContainerPaper(player -> player.openAnvil(null, true));
    public static final AdditionalContainerPaper STONE_CUTTER = new AdditionalContainerPaper(player -> player.openStonecutter(null, true));
    public static final AdditionalContainerPaper GRINDSTONE = new AdditionalContainerPaper(player -> player.openGrindstone(null, true));
    public static final AdditionalContainerPaper CARTOGRAPHY_TABLE = new AdditionalContainerPaper(player -> player.openCartographyTable(null, true));
    public static final AdditionalContainerPaper LOOM = new AdditionalContainerPaper(player -> player.openLoom(null, true));
    public static final AdditionalContainerPaper SMITHING_TABLE = new AdditionalContainerPaper(player -> player.openSmithingTable(null, true));

    public static final AdditionalContainerPaper ELDER_GUARDIAN = new AdditionalContainerPaper(player -> player.showElderGuardian(false));
    public static final AdditionalContainerPaper ELDER_GUARDIAN_SILENT = new AdditionalContainerPaper(player -> player.showElderGuardian(true));

    private static final Environment ENVIRONMENT = PaperLib.getEnvironment();
    private static final Logger LOGGER = Logger.getLogger("AdditionalContainerPaper");

    private final Consumer<Player> consumer;

    public AdditionalContainerPaper(Consumer<Player> consumer) {
        this.consumer = consumer;
    }

    @SuppressWarnings("deprecation")
    public void open(Player player) {
        if (!ENVIRONMENT.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            LOGGER.warning("This feature is only available on paper, use paper or other paper 1-17-1.19x forks");

            return;
        }

        this.consumer.accept(player);
    }
}
