package com.eternalcode.core.placeholder.cache;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalReloadEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class AsyncPlaceholderCacheController implements Listener {

    private final AsyncPlaceholderCacheRegistry cacheRegistry;

    @Inject
    AsyncPlaceholderCacheController(AsyncPlaceholderCacheRegistry cacheRegistry) {
        this.cacheRegistry = cacheRegistry;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(PlayerQuitEvent event) {
        this.cacheRegistry.invalidatePlayer(event.getPlayer().getUniqueId());
    }

    @Subscribe
    void onDisable(EternalShutdownEvent event) {
        this.cacheRegistry.invalidateAll();
    }

    @Subscribe
    void onReload(EternalReloadEvent event) {
        this.cacheRegistry.invalidateAll();
    }
}
