package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.position.Position;
import com.eternalcode.core.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class SpawnServiceImpl implements SpawnService {

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    public SpawnServiceImpl(LocationsConfiguration locationsConfiguration, ConfigurationManager configurationManager) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public void teleportToSpawn(Player player) {
        player.teleport(PositionAdapter.convert(this.locationsConfiguration.spawn));
    }

    @Override
    public void setSpawnLocation(Position position) {
        this.locationsConfiguration.spawn = position;
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.locationsConfiguration.spawn = PositionAdapter.convert(location);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public Position getSpawnLocationAsPosition() {
        return this.locationsConfiguration.spawn;
    }

    @Override
    public Location getSpawnLocationAsLocation() {
        Position spawn = this.locationsConfiguration.spawn;
        return PositionAdapter.convert(spawn);
    }
}
