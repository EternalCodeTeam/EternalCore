package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Blocking;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;

@Service
class JailServiceImpl implements JailService {

    private final Map<UUID, JailedPlayer> jailedPlayers = new HashMap<>();

    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final JailSettings settings;
    private final EventCaller eventCaller;
    private final Server server;

    private final PrisonerRepository prisonerRepository;
    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    JailServiceImpl(
        TeleportService teleportService,
        SpawnService spawnService,
        JailSettings settings,
        EventCaller eventCaller,
        Server server,
        PrisonerRepository prisonerRepository,
        LocationsConfiguration locationsConfiguration,
        ConfigurationManager configurationManager
    ) {
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.settings = settings;
        this.eventCaller = eventCaller;
        this.server = server;
        this.prisonerRepository = prisonerRepository;
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;

        this.loadFromDatabase();
    }

    @Override
    public boolean detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration time) {
        if (time == null) {
            time = this.settings.defaultJailDuration();
        }

        Optional<Location> jailAreaLocation = this.getJailAreaLocation();

        if (jailAreaLocation.isEmpty()) {
            return false;
        }

        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, detainedBy);
        this.eventCaller.callEvent(jailDetainEvent);

        if (jailDetainEvent.isCancelled()) {
            return false;
        }

        JailedPlayer jailedPlayer = new JailedPlayer(player.getUniqueId(), Instant.now(), time, detainedBy.getName());

        this.prisonerRepository.savePrisoner(jailedPlayer);
        this.jailedPlayers.put(player.getUniqueId(), jailedPlayer);

        this.teleportService.teleport(player, jailAreaLocation.get());
        return true;
    }

    @Override
    public boolean releasePlayer(Player player) {
        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player.getUniqueId());
        this.eventCaller.callEvent(jailReleaseEvent);

        if (jailReleaseEvent.isCancelled()) {
            return false;
        }

        this.prisonerRepository.deletePrisoner(player.getUniqueId());
        this.jailedPlayers.remove(player.getUniqueId());

        this.spawnService.teleportToSpawn(player);

        return true;
    }

    @Override
    public void releaseAllPlayers() {
        this.jailedPlayers.forEach((uuid, prisoner) -> {
            Player jailedPlayer = this.server.getPlayer(uuid);
            JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(uuid);
            this.eventCaller.callEvent(jailReleaseEvent);

            if (jailReleaseEvent.isCancelled()) {
                return;
            }

            if (jailedPlayer != null) {
                this.teleportService.teleport(jailedPlayer, this.spawnService.getSpawnLocation());
            }
        });

        this.jailedPlayers.clear();
        this.prisonerRepository.deleteAllPrisoners();
    }

    @Override
    public boolean isPlayerJailed(UUID player) {
        JailedPlayer jailedPlayer = this.jailedPlayers.get(player);

        if (jailedPlayer == null || jailedPlayer.isPrisonExpired()) {
            return false;
        }

        return true;
    }

    @Override
    public Collection<JailedPlayer> getJailedPlayers() {
        return Collections.unmodifiableCollection(this.jailedPlayers.values());
    }

    private void loadFromDatabase() {
        this.prisonerRepository.getPrisoners().thenAccept(prisoners -> {
            for (JailedPlayer jailedPlayer : prisoners) {
                this.jailedPlayers.put(jailedPlayer.getPlayerUniqueId(), jailedPlayer);
            }
        });
    }

    @Override
    public Optional<Location> getJailAreaLocation() {
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
