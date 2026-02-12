package com.eternalcode.example.feature.teleportrequest;

import com.eternalcode.core.feature.teleportrequest.PreTeleportRequestEvent;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApiTeleportRequestListener implements Listener {

    @EventHandler
    public void onPreTeleportRequest(final PreTeleportRequestEvent event) {
        final Player sender = event.getSender();
        final Player target = event.getTarget();

        sender.sendMessage(String.format(
            "PreTeleportRequestEvent: %s wants to teleport to %s (EternalCore API).",
            sender.getName(),
            target.getName()
        ));

        /*
        if (!countryService.areInSameCountry(sender, target)) {
            sender.sendMessage("You are not in the same country!");
            event.setCancelled(true);
        }
        */
    }

    @EventHandler
    public void onTeleportRequest(final TeleportRequestEvent event) {
        final Player sender = event.getSender();
        final Player target = event.getTarget();

        sender.sendMessage(String.format(
            "TeleportRequestEvent: %s sent a teleport request to %s (EternalCore API).",
            sender.getName(),
            target.getName()
        ));
    }
}
