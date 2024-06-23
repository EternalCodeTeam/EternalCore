package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class SpawnServiceImpl implements SpawnService {

    public static final Logger LOGGER = Logger.getLogger(SpawnServiceImpl.class.getName());
    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    public SpawnServiceImpl(LocationsConfiguration locationsConfiguration, ConfigurationManager configurationManager) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public void teleportToSpawn(Player player) {
        Position spawn = this.locationsConfiguration.spawn;

        if (spawn.isNoneWorld()) {
            LOGGER.warning("The spawn location is not set, set it with /setspawn!");

            return;
        }

        player.teleport(PositionAdapter.convert(spawn));
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.locationsConfiguration.spawn = PositionAdapter.convert(location);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public Location getSpawnLocation() {
        Position spawn = this.locationsConfiguration.spawn;
        return PositionAdapter.convert(spawn);
    }
}
