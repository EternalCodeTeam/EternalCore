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
     * In this example, we will use a button.
     */
    @EventHandler
    public void onPlayerClickButton(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        Material type = clickedBlock.getType();
        boolean tagged = Tag.BUTTONS.isTagged(type);

        if (!tagged) {
            return;
        }

        Player player = event.getPlayer();
        this.randomTeleportService.teleport(player);
    }
}
