package com.eteranlcode.containers;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class AdditionalContainerPaper {

    public static void openAdditionalContainer(Player player, @NotNull AdditionalContainerType type) {
        switch (type) {
            case ANVIL -> player.openAnvil(null, true);
            case STONECUTTER -> player.openStonecutter(null, true);
            case CARTOGRAPHY_TABLE -> player.openCartographyTable(null, true);
            case GRINDSTONE -> player.openGrindstone(null, true);
            case LOOM -> player.openLoom(null, true);
            case SMITHING_TABLE -> player.openSmithingTable(null, true);
        }
    }
    
}
