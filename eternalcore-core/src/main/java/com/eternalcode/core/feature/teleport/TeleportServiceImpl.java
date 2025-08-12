package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleport.apiteleport.TeleportCommandService;
import com.eternalcode.core.feature.teleport.apiteleport.TeleportResult;
import com.eternalcode.core.feature.teleport.apiteleport.TeleportService;
import com.eternalcode.core.feature.teleport.apiteleport.event.EternalTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
class TeleportServiceImpl implements TeleportService {

    private final Map<UUID, Position> lastPosition = new HashMap<>();

    private final TeleportCommandService teleportCommandService;
    private final TeleportTaskService teleportTaskService;
    private final EventCaller eventCaller;

    @Inject
    public TeleportServiceImpl(
        TeleportCommandService teleportCommandService,
        TeleportTaskService teleportTaskService,
        EventCaller eventCaller
    ) {
        this.teleportCommandService = teleportCommandService;
        this.teleportTaskService = teleportTaskService;
        this.eventCaller = eventCaller;
    }

    @Override
    public void teleport(Player player, Location location) {
        EternalTeleportEvent event = this.eventCaller.callEvent(new EternalTeleportEvent(player, location));

        if (event.isCancelled()) {
            return;
        }

        Location last = player.getLocation().clone();

        PaperLib.teleportAsync(player, event.getLocation());

        this.markLastLocation(player.getUniqueId(), last);
    }

    @Override
    public CompletableFuture<TeleportResult> teleportWithDelay(Player player, Location location) {
        return this.teleportWithDelay(player, location, "default");
    }

    public CompletableFuture<TeleportResult> teleportWithDelay(Player player, Location location, String command) {
        Duration delay = this.teleportCommandService.getTeleportDelay(player, command);
        return this.teleportWithDelay(player, location, delay);
    }

    @Override
    public CompletableFuture<TeleportResult> teleportWithDelay(Player player, Location location, Duration delay) {
        // Jeśli opóźnienie wynosi 0, teleportuj natychmiastowo
        if (delay.isZero()) {
            this.teleport(player, location);
            return CompletableFuture.completedFuture(TeleportResult.SUCCESS);
        }

        // Utwórz zadanie teleportacji z opóźnieniem
        Position startPosition = PositionAdapter.convert(player.getLocation());
        Position destinationPosition = PositionAdapter.convert(location);

        Teleport teleport = this.teleportTaskService.createTeleport(
            player.getUniqueId(),
            startPosition,
            destinationPosition,
            delay
        );

        return teleport.getResult();
    }

    @Override
    public Optional<Location> getLastLocation(UUID player) {
        return Optional.ofNullable(this.lastPosition.get(player))
            .map(position -> PositionAdapter.convert(position));
    }

    @Override
    public void markLastLocation(UUID player, Location location) {
        this.lastPosition.put(player, PositionAdapter.convert(location));
    }
}
