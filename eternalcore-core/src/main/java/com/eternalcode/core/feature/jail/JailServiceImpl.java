package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Service
public class JailServiceImpl implements JailService {

    private final JailLocationRepository jailRepository;

    private Position jailPosition;

    @Inject
    public JailServiceImpl(JailLocationRepository jailRepository) {
        this.jailRepository = jailRepository;

        this.loadFromDatabase();
    }

    @Override
    public Location getJailPosition() {
        return PositionAdapter.convert(this.jailPosition);
    }

    @Override
    public void setupJailArea(Location jailLocation, Player setter) {

        Position position = new Position(jailLocation.getX(), jailLocation.getY(), jailLocation.getZ(), jailLocation.getYaw(), jailLocation.getPitch(), setter.getWorld().getName());

        this.jailRepository.setJailLocation(position);
        this.jailPosition = position;
    }

    @Override
    public void removeJailArea(Player remover) {
        this.jailPosition = null;
        this.jailRepository.deleteJailLocation();
    }

    @Override
    public boolean isLocationSet() {
        return this.jailPosition != null;
    }

    private void loadFromDatabase() {
        this.jailRepository.getJailLocation().whenComplete((position, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            position.ifPresent(value -> this.jailPosition = value);
        });
    }
}
