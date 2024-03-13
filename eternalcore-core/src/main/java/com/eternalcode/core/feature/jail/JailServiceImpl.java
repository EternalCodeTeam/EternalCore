package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    public Location getJailLocation() {
        return PositionAdapter.convert(this.locationsConfiguration.jail);
    }

    @Override
    public void setupJailArea(Location jailLocation) {
        this.locationsConfiguration.jail = PositionAdapter.convert(jailLocation);
        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public void removeJailArea() {
        this.locationsConfiguration.jail = null;
    }

    @Override
    public boolean isJailLocationSet() {
        return !this.locationsConfiguration.jail.isNoneWorld();
    }
}
