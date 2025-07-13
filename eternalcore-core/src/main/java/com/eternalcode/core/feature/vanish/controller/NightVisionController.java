package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Controller
// TODO: if-check
public class NightVisionController implements Listener {

    private static final PotionEffect NIGHT_VISION_EFFECT = new PotionEffect(
            PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false
    );

    @EventHandler
    void onEnable(EnableVanishEvent event) {
        Player player = event.getPlayer();

        player.addPotionEffect(NIGHT_VISION_EFFECT);
    }

    @EventHandler
    void onDisable(DisableVanishEvent event) {
        Player player = event.getPlayer();

        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }
}
