package com.eternalcode.core.feature.kit;

import java.util.List;
import org.bukkit.entity.Player;

public class KitGui {

    private final List<String> availableKits;
    private final KitSettings kitSettings;

    KitGui(List<String> availableKits, KitSettings kitSettings) {
        this.availableKits = availableKits;
        this.kitSettings = kitSettings;
    }

    void open(Player player) {

    }
}
