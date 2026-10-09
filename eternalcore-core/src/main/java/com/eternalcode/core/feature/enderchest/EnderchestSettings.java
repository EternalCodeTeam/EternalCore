package com.eternalcode.core.feature.enderchest;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;

public interface EnderchestSettings {

    boolean replaceVanillaEnderchest();

    boolean enderchestsBlocked();

    boolean sharedViewingBlocked();

    PageSettings pages();

    interface PageSettings {

        int rows();

        String title();

        Duration switchDelay();

        Map<String, Integer> limits();

        int defaultLimit();

        NavigationSettings navigation();
    }

    interface NavigationSettings {

        ItemSettings nextPage();

        ItemSettings previousPage();
    }

    interface ItemSettings {

        Material material();

        String name();

        List<String> lore();

        boolean glow();
    }
}
