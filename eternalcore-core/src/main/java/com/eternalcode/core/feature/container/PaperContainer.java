package com.eternalcode.core.feature.container;

import org.bukkit.entity.Player;

public enum PaperContainer {

    ANVIL {
        @Override
        public void open(Player player) {
            player.openAnvil(null, true);
        }
    },
    STONE_CUTTER {
        @Override
        public void open(Player player) {
            player.openStonecutter(null, true);
        }
    },
    GRINDSTONE {
        @Override
        public void open(Player player) {
            player.openGrindstone(null, true);
        }
    },
    CARTOGRAPHY_TABLE {
        @Override
        public void open(Player player) {
            player.openCartographyTable(null, true);
        }
    },
    LOOM {
        @Override
        public void open(Player player) {
            player.openLoom(null, true);
        }
    },
    SMITHING_TABLE {
        @Override
        public void open(Player player) {
            player.openSmithingTable(null, true);
        }
    };

    public abstract void open(Player player);

}
