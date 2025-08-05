package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Controller
class NightVisionController implements Listener {

    private static final PotionEffect NIGHT_VISION_EFFECT =
        new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false);

    private final VanishSettings settings;

    @Inject
    NightVisionController(VanishSettings settings) {
        this.settings = settings;
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.settings.nightVision()) {
            return;
        }

        Player player = event.getPlayer();

        player.addPotionEffect(NIGHT_VISION_EFFECT);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        if (!this.settings.nightVision()) {
            return;
        }

        Player player = event.getPlayer();

        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
