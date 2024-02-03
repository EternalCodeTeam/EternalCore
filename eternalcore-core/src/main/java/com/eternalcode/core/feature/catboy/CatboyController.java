package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

@Controller
class CatboyController implements Listener {

    private final CatboyService catboyService;

    @Inject
    CatboyController(CatboyService catboyService) {
        this.catboyService = catboyService;
    }

    @EventHandler
    void onCatboyEvent(PlayerQuitEvent event) {
        this.catboyService.unmarkAsCatboy(event.getPlayer());
    }

    @EventHandler
    void onCatboyEvent(EntityDismountEvent event) {
        if (!(event.getDismounted() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof Cat cat)) {
            return;
        }

        if (this.catboyService.isCatboy(player.getUniqueId())) {
            cat.remove();
        }
    }

}
