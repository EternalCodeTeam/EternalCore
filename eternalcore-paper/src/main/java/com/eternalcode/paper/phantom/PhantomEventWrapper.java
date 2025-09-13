package com.eternalcode.paper.phantom;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

class PhantomEventWrapper implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    void onPhantomSpawn(PhantomPreSpawnEvent event) {
        PhantomSpawnAttemptEvent attemptEvent = new PhantomSpawnAttemptEvent(
            event.getSpawningEntity(),
            event.getSpawnLocation(),
            event.getReason()
        );

        Bukkit.getPluginManager().callEvent(attemptEvent);

        if (attemptEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }
}
