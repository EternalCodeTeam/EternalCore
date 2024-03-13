package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class JailServiceImpl implements JailService {

    private final LocationsConfiguration locationsConfiguration;

    @Inject
    public JailServiceImpl(LocationsConfiguration locationsConfiguration) {
        this.locationsConfiguration = locationsConfiguration;
    }

    @Override
    public Location getJailPosition() {
        return PositionAdapter.convert(this.locationsConfiguration.jail);
    }

    @Override
    public void setupJailArea(Location jailLocation, Player setter) {

        this.locationsConfiguration.jail = new Position(jailLocation.getX(), jailLocation.getY(), jailLocation.getZ(), jailLocation.getYaw(), jailLocation.getPitch(), setter.getWorld().getName());
    }

    @Override
    public void removeJailArea(Player remover) {
        this.locationsConfiguration.jail = null;
    }

    @Override
    public boolean isLocationSet() {
        return this.locationsConfiguration.jail != null && !this.locationsConfiguration.jail.isNoneWorld();
    }

}
