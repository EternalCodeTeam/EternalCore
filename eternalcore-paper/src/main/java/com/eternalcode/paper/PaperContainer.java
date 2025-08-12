package com.eternalcode.paper;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum PaperContainer {

    ANVIL("Anvil", player -> player.openAnvil(null, true)),
    STONE_CUTTER("Stone Cutter", player -> player.openStonecutter(null, true)),
    GRINDSTONE("Grindstone", player -> player.openGrindstone(null, true)),
    CARTOGRAPHY_TABLE("Cartography Table", player -> player.openCartographyTable(null, true)),
    LOOM("Loom", player -> player.openLoom(null, true)),
    SMITHING_TABLE("Smithing Table", player -> player.openSmithingTable(null, true));

    private final PaperFeature feature;

    PaperContainer(String name, Consumer<Player> action) {
        this.feature = new PaperFeature(action, name) {
        };
    }

    public void open(Player player) {
        feature.execute(player);
    }
}
