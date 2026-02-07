package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service
public class SpawnServiceImpl implements SpawnService {

    private static final String WARNING = "The spawn location is not set, set it with /setspawn!";

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;
    private final Logger logger;

    @Inject
    public SpawnServiceImpl(LocationsConfiguration locationsConfiguration, ConfigurationManager configurationManager, Logger logger) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
        this.logger = logger;
    }

    @Override
    public void teleportToSpawn(@NotNull Player player) {
        if (!isSpawnValid()) {
            this.logger.warning(WARNING);
            return;
        }

        Location spawnLocation = PositionAdapter.convert(this.locationsConfiguration.spawn);
        PaperLib.teleportAsync(player, spawnLocation);
    }

    @Override
    public void setSpawnLocation(@NotNull Location location) {
        this.locationsConfiguration.spawn = PositionAdapter.convert(location);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public @Nullable Location getSpawnLocation() {
        if (!isSpawnValid()) {
            this.logger.warning(WARNING);
            return null;
        }

        return PositionAdapter.convert(this.locationsConfiguration.spawn);
    }

    private boolean isSpawnValid() {
        return !this.locationsConfiguration.spawn.isNoneWorld();
    }
}
