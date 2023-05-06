package com.eternalcode.containers;


import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class AdditionalContainerPaper {

    public static final AdditionalContainerPaper ANVIL = new AdditionalContainerPaper("anvil", player -> player.openAnvil(null, true));
    public static final AdditionalContainerPaper STONE_CUTTER = new AdditionalContainerPaper("stone_cutter", player -> player.openStonecutter(null, true));
    public static final AdditionalContainerPaper GRINDSTONE = new AdditionalContainerPaper("grindstone", player -> player.openGrindstone(null, true));
    public static final AdditionalContainerPaper CARTOGRAPHY_TABLE = new AdditionalContainerPaper("cartography_table", player -> player.openCartographyTable(null, true));
    public static final AdditionalContainerPaper LOOM = new AdditionalContainerPaper("loom", player -> player.openLoom(null, true));
    public static final AdditionalContainerPaper SMITHING_TABLE = new AdditionalContainerPaper("smithing_table", player -> player.openSmithingTable(null, true));

    private static final Environment ENVIRONMENT = PaperLib.getEnvironment();
    private static final Logger LOGGER = Logger.getLogger("AdditionalContainerPaper");

    private final String name;
    private final Consumer<Player> consumer;

    public AdditionalContainerPaper(String name, Consumer<Player> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public String name() {
        return this.name;
    }

    public void open(Player player) {
        if (!ENVIRONMENT.isPaper()) {
            player.sendMessage(ChatColor.RED + "This feature is not supported on this server. Please contact the server administrator and check console!");
            LOGGER.warning("This feature is only available on paper, use paper or other paper 1-17-1.19x forks");

            return;
        }

        this.consumer.accept(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdditionalContainerPaper that = (AdditionalContainerPaper) o;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
