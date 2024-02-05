package com.eternalcode.example.feature.randomteleport;

import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.randomteleport.event.PreRandomTeleportEvent;
import com.eternalcode.core.feature.randomteleport.event.RandomTeleportEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ApiRandomTeleportListener implements Listener {

    private final RandomTeleportService randomTeleportService;

    public ApiRandomTeleportListener(RandomTeleportService randomTeleportService) {
        this.randomTeleportService = randomTeleportService;
    }

    @EventHandler
    public void onPreRandomTeleportEvent(PreRandomTeleportEvent event) {
        Player player = event.getPlayer();

        String message = "Started searching random location, executed via "
            + "PreRandomTeleportEvent from eternalcore developer api bridge.";
        player.sendMessage(message);
    }

    @EventHandler
    public void onRandomTeleportEvent(RandomTeleportEvent event) {
        Player player = event.getPlayer();
        Location teleportLocation = event.getTeleportLocation();

        String message = "Teleported to random location, executed via"
            + " RandomTeleportEvent from eternalcore developer api bridge.";

        player.sendMessage(message);
        player.sendMessage(String.format("Teleport location: %s", teleportLocation.toString()));
    }

    /**
     * You can teleport the player to a random location using some actions.
     * In this example, we will use a button on an obsidian block.
     */
    @EventHandler
    public void onPlayerClickObsidianButton(PlayerInteractEvent event) {
        // if player clicks stone button on obisidian block
        Block clickedBlock = event.getClickedBlock();

        boolean tagged = Tag.BUTTONS.isTagged(clickedBlock.getType());

        if (!tagged) {
            return;
        }

        // get button and check if button is on obsidian block
        Block buttonBlock = clickedBlock.getRelative(event.getBlockFace());

        if (buttonBlock.getType() == Material.OBSIDIAN) {
            Player player = event.getPlayer();

            this.randomTeleportService.teleport(player);
        }
    }
}
