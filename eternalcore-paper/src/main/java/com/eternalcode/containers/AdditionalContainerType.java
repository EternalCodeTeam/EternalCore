package com.eternalcode.containers;


import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.function.Consumer;

public class AdditionalContainerType {

    public static final AdditionalContainerType ANVIL = new AdditionalContainerType("anvil", player -> player.openAnvil(null, true));
    public static final AdditionalContainerType STONE_CUTTER = new AdditionalContainerType("stone_cutter", player -> player.openStonecutter(null, true));
    public static final AdditionalContainerType GRINDSTONE = new AdditionalContainerType("grindstone", player -> player.openGrindstone(null, true));
    public static final AdditionalContainerType CARTOGRAPHY_TABLE = new AdditionalContainerType("cartography_table", player -> player.openCartographyTable(null, true));
    public static final AdditionalContainerType LOOM = new AdditionalContainerType("loom", player -> player.openLoom(null, true));
    public static final AdditionalContainerType SMITHING_TABLE = new AdditionalContainerType("smithing_table", player -> player.openSmithingTable(null, true));
    public static final AdditionalContainerType ENCANTING_TABLE = new AdditionalContainerType("enchanting_table", player -> player.openEnchanting(null, true));

    private final String name;
    private final Consumer<Player> consumer;

    public AdditionalContainerType(String name, Consumer<Player> consumer) {
        this.name = name;
        this.consumer = consumer;
    }

    public String name() {
        return this.name;
    }

    public void open(Player player) {
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

        AdditionalContainerType that = (AdditionalContainerType) o;
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
