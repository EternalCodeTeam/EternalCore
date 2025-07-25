package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
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
    private final VanishConfiguration config;

    @Inject
    public BlockController(VanishService vanishService, VanishConfiguration config) {
        this.vanishService = vanishService;
        this.config = config;
    }

    @EventHandler
    void onBreak(BlockBreakEvent event) {
        if (!this.config.blockBreak) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onPlace(BlockPlaceEvent event) {
        if (!this.config.blockPlace) {
            return;
        }

        Player player = event.getPlayer();

        if (!this.vanishService.isVanished(player)) {
            return;
        }

        event.setCancelled(true);
    }
}
