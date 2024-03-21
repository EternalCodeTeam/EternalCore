package com.eternalcode.example.feature.ignore;

import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApiIgnoreListener implements Listener {

    /**
     * This method is called when a player is ignored.
     **/

    @EventHandler
    public void onIgnore(IgnoreEvent event) {
        Player by = event.getBy();
        Player target = event.getTarget();

        by.playSound(by.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);
        target.getWorld().createExplosion(target.getLocation(), 2.0F);
    }

    /**
     * This method is called when a player is unignored.
     **/

    @EventHandler
    public void onUnIgnore(IgnoreEvent event) {
        Player by = event.getBy();
        Player target = event.getTarget();

        by.playSound(by.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
        target.getWorld().strikeLightning(target.getLocation());
    }

}
