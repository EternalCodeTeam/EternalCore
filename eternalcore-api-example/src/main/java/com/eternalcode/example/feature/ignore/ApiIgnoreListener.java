package com.eternalcode.example.feature.ignore;

import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import java.util.UUID;
import org.bukkit.Bukkit;
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
        UUID requesterId = event.getRequester();
        UUID targetId = event.getTarget();
        Player requester = Bukkit.getPlayer(requesterId);
        Player target = Bukkit.getPlayer(targetId);

        if (requester == null || target == null) {
            return;
        }

        requester.playSound(requester.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1.0F, 1.0F);
        target.getWorld().createExplosion(target.getLocation(), 2.0F);
    }

    /**
     * This method is called when a player ignores all players
     **/

    @EventHandler
    public void onIgnoreAll(IgnoreAllEvent event) {
        UUID requesterId = event.getRequester();
        Player requester = Bukkit.getPlayer(requesterId);

        if (requester == null) {
            return;
        }

        requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        requester.getWorld().strikeLightning(requester.getLocation());
    }

    /**
     * This method is called when a player is unignored.
     **/

    @EventHandler
    public void onUnIgnore(UnIgnoreEvent event) {
        UUID requesterId = event.getRequester();
        UUID targetId = event.getTarget();
        Player requester = Bukkit.getPlayer(requesterId);
        Player target = Bukkit.getPlayer(targetId);

        if (requester == null || target == null) {
            return;
        }

        requester.playSound(requester.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F);
        target.getWorld().strikeLightning(target.getLocation());
    }

    /**
     * This method is called when a player unignores all players
     **/

    @EventHandler
    public void onUnIgnoreAll(UnIgnoreAllEvent event) {
        UUID requesterId = event.getRequester();
        Player requester = Bukkit.getPlayer(requesterId);

        if (requester == null) {
            return;
        }

        requester.playSound(requester.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1.0F, 1.0F);
        requester.getWorld().createExplosion(requester.getLocation(), 2.0F);
    }

}
