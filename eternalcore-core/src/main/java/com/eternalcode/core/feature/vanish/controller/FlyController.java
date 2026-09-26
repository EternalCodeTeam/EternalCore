package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Controller
class FlyController implements Listener {

    private final VanishSettings settings;
    private final Map<UUID, FlightState> previousFlightStates = new ConcurrentHashMap<>();

    @Inject
    FlyController(VanishSettings settings) {
        this.settings = settings;
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.settings.flyMode()) {
            return;
        }

        Player player = event.getPlayer();

        this.previousFlightStates.computeIfAbsent(
            player.getUniqueId(),
            id -> new FlightState(player.getAllowFlight(), player.isFlying())
        );

        player.setAllowFlight(true);
        player.setFlying(true);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            this.previousFlightStates.remove(player.getUniqueId());
            return;
        }

        FlightState state = this.previousFlightStates.remove(player.getUniqueId());
        if (state == null) {
            return;
        }

        player.setAllowFlight(state.hadAllowFlight());
        player.setFlying(state.wasFlying());
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        this.previousFlightStates.remove(event.getPlayer().getUniqueId());
    }

    private record FlightState(boolean hadAllowFlight, boolean wasFlying) {
    }
}
