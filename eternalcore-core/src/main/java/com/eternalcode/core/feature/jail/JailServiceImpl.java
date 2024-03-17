package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.jetbrains.annotations.Blocking;

import java.util.Optional;

@Service
public class JailServiceImpl implements JailService {

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    public JailServiceImpl(LocationsConfiguration locationsConfiguration, ConfigurationManager configurationManager) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public Optional<Location> getJailLocation() {
        Position position = this.locationsConfiguration.jail;

        if (position.isNoneWorld()) {
            return Optional.empty();
        }

        return Optional.of(PositionAdapter.convert(position));
    }

    @Override
    @Blocking
    public void setupJailArea(Location jailLocation) {
        this.locationsConfiguration.jail = PositionAdapter.convert(jailLocation);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    @Blocking
    public void removeJailArea() {
        this.locationsConfiguration.jail = LocationsConfiguration.EMPTY_POSITION;
        this.configurationManager.save(this.locationsConfiguration);
    }
}
