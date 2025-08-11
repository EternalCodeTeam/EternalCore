package com.eternalcode.paper;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum PaperOverlay {

    ELDER_GUARDIAN("Elder Guardian", player -> player.showElderGuardian(false)),
    ELDER_GUARDIAN_SILENT("Elder Guardian Silent", player -> player.showElderGuardian(true)),
    END_SCREEN("End Screen", player -> player.showWinScreen());

    private final PaperFeature feature;

    PaperOverlay(String name, Consumer<Player> action) {
        this.feature = new PaperFeature(action, name) {
        };
    }

    public void show(Player player) {
        this.feature.execute(player);
    }
}
