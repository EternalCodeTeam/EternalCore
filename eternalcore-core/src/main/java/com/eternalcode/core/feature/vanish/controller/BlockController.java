package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

@Controller
public class BlockController implements Listener {

    private final VanishService vanishService;

    @Inject
    public BlockController(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }
}
