package com.eternalcode.core.feature.home;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;

@Controller
public class HomeController implements Listener {

    private final HomeManager homeManager;

    @Inject
    public HomeController(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @EventHandler
    void onWorldDelete(WorldUnloadEvent event) {
        this.homeManager.removeHomesInWorld(event.getWorld().getName());
    }
}
