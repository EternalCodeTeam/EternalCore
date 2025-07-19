package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

// TODO: if-check
@Controller
public class ItemController implements Listener {
    
    private final VanishService vanishService;

    @Inject
    public ItemController(VanishService vanishService) {
        this.vanishService = vanishService;
    }
    
    @EventHandler
    void onPickUp(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!this.vanishService.isVanished(player)) {
            return;
        }
        
        event.setCancelled(true);
    }

    @EventHandler
    void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }
 }
