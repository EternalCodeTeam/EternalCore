package com.eternalcode.core.teleport;

import com.eternalcode.core.shared.Adapter;
import com.eternalcode.core.shared.Position;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportService {

    private final Map<UUID, Position> lastPosition = new HashMap<>();

    public void teleport(Player player, Location location) {
        Location last = player.getLocation().clone();

        PaperLib.teleportAsync(player, location);
        this.markLastLocation(player.getUniqueId(), last);
    }

    public Option<Location> getLastLocation(UUID player) {
        return Option.of(this.lastPosition.get(player)).map(Adapter::convert);
    }

    public void markLastLocation(UUID player, Location location) {
        this.lastPosition.put(player, Adapter.convert(location));
    }

}
