package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@Controller
class VanishFlightController implements Listener {

    private final Map<UUID, FlightState> previousFlightStates = new HashMap<>();

    private final VanishSettings settings;
    private final Server server;

    @Inject
    VanishFlightController(VanishSettings settings, Server server) {
        this.settings = settings;
        this.server = server;
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.settings.allowFlight()) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        this.previousFlightStates.putIfAbsent(
            playerId,
            new FlightState(player.getAllowFlight(), player.isFlying())
        );

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        this.restoreFlightState(event.getPlayer());
    }

    void synchronize(VanishService vanishService) {
        Iterator<Map.Entry<UUID, FlightState>> iterator = this.previousFlightStates.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<UUID, FlightState> entry = iterator.next();
            UUID playerId = entry.getKey();
            Player player = this.server.getPlayer(playerId);

            if (player == null || !player.isOnline()) {
                iterator.remove();
                continue;
            }

            if (!this.settings.allowFlight() || !vanishService.isVanished(playerId)) {
                entry.getValue().apply(player);
                iterator.remove();
                continue;
            }

            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
        }
    }

    private void restoreFlightState(Player player) {
        FlightState previousFlightState = this.previousFlightStates.remove(player.getUniqueId());
        if (previousFlightState == null) {
            return;
        }

        previousFlightState.apply(player);
    }

    private record FlightState(boolean allowFlight, boolean flying) {

        private void apply(Player player) {
            if (this.allowFlight) {
                player.setAllowFlight(true);
                player.setFlying(this.flying);
                return;
            }

            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }
}
