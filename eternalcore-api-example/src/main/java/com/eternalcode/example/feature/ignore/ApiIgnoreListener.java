package com.eternalcode.example.feature.ignore;

import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
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
     * This method is called when a player ignores all players
     **/

    @EventHandler
    public void onIgnoreAll(IgnoreAllEvent event) {
        Player by = event.getBy();

        by.playSound(by.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        by.getWorld().strikeLightning(by.getLocation());
    }

    /**
     * This method is called when a player is unignored.
     **/

    @EventHandler
    public void onUnIgnore(UnIgnoreEvent event) {
        Player by = event.getBy();
        Player target = event.getTarget();

        by.playSound(by.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
        target.getWorld().strikeLightning(target.getLocation());
    }

    /**
     * This method is called when a player unignores all players
     **/

    @EventHandler
    public void onUnIgnoreAll(UnIgnoreAllEvent event) {
        Player by = event.getBy();

        by.playSound(by.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);
        by.getWorld().createExplosion(by.getLocation(), 2.0F);
    }

}
