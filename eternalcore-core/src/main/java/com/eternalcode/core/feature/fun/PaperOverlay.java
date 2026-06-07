package com.eternalcode.core.feature.fun;

import org.bukkit.entity.Player;

public enum PaperOverlay {

    ELDER_GUARDIAN {
        @Override
        public void show(Player player) {
            player.showElderGuardian(false);
        }
    },
    ELDER_GUARDIAN_SILENT {
        @Override
        public void show(Player player) {
            player.showElderGuardian(true);
        }
    },
    END_SCREEN {
        @Override
        public void show(Player player) {
            player.showWinScreen();
        }
    };

    public abstract void show(Player player);

}
