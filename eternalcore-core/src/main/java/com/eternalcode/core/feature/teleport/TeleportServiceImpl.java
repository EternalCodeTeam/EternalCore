package com.eternalcode.core.feature.teleport;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.teleport.event.EternalTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import io.papermc.lib.PaperLib;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
class TeleportServiceImpl implements TeleportService {

    private final Map<UUID, Position> lastPosition = new HashMap<>();

    private final EventCaller eventCaller;

    @Inject
    TeleportServiceImpl(EventCaller eventCaller) {
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
    public Optional<Location> getLastLocation(UUID player) {
        return Optional.ofNullable(this.lastPosition.get(player)).map(position -> PositionAdapter.convert(position));
    }

    @Override
    public void markLastLocation(UUID player, Location location) {
        this.lastPosition.put(player, PositionAdapter.convert(location));
    }
}
