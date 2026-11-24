package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.spawn.event.PreSpawnTeleportEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.logging.Logger;

@Service
public class SpawnServiceImpl implements SpawnService {

    private static final String WARNING = "The spawn location is not set, set it with /setspawn!";

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;
    private final Logger logger;
    private final EventCaller eventCaller;

    @Inject
    public SpawnServiceImpl(LocationsConfiguration locationsConfiguration, ConfigurationManager configurationManager, Logger logger, EventCaller eventCaller) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
        this.logger = logger;
        this.eventCaller = eventCaller;
    }

    @Override
    public void teleportToSpawn(Player player) {
        if (!isSpawnValid()) {
            this.logger.warning(WARNING);
            return;
        }

        Location spawnLocation = PositionAdapter.convert(this.locationsConfiguration.spawn);
        PreSpawnTeleportEvent event = this.eventCaller.callEvent(new PreSpawnTeleportEvent(player, spawnLocation));
        if (event.isCancelled()) {
            return;
        }

        player.teleportAsync(event.getDestination());
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.locationsConfiguration.spawn = PositionAdapter.convert(location);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public Location getSpawnLocation() {
        if (!isSpawnValid()) {
            this.logger.warning(WARNING);
            return null;
        }

        return PositionAdapter.convert(this.locationsConfiguration.spawn);
    }

    private boolean isSpawnValid() {
        return this.locationsConfiguration.spawn != null
            && !this.locationsConfiguration.spawn.isNoneWorld();
    }
}
