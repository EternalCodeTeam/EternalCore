package com.eternalcode.core.feature.jail;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    private final Map<UUID, Prisoner> jailedPlayers = new HashMap<>();

    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final JailService jailService;
    private final JailSettings settings;
    private final EventCaller eventCaller;
    private final Server server;

    private final PrisonersRepository prisonersRepository;

    @Inject
    public PrisonerServiceImpl(
        TeleportService teleportService,
        SpawnService spawnService,
        JailService jailService,
        JailSettings settings,
        EventCaller eventCaller,
        Server server,
        PrisonersRepository prisonersRepository
    ) {
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.jailService = jailService;
        this.eventCaller = eventCaller;
        this.settings = settings;
        this.server = server;
        this.prisonersRepository = prisonersRepository;

        this.loadFromDatabase();
    }

    @Override
    public boolean detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration time) {
        if (time == null) {
            time = this.settings.defaultJailDuration();
        }

        if (this.jailService.getJailLocation().isPresent()) {
            return false;
        }

        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, detainedBy);
        this.eventCaller.callEvent(jailDetainEvent);

        if (jailDetainEvent.isCancelled()) {
            return false;
        }

        Prisoner prisoner = new Prisoner(player.getUniqueId(), Instant.now(), time, detainedBy.getName());

        this.prisonersRepository.savePrisoner(prisoner);
        this.jailedPlayers.put(player.getUniqueId(), prisoner);

        this.teleportService.teleport(player, this.jailService.getJailLocation().get());
        return true;
    }

    @Override
    public boolean releasePlayer(Player player, @Nullable CommandSender releasedBy) {
        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player.getUniqueId());
        this.eventCaller.callEvent(jailReleaseEvent);

        if (jailReleaseEvent.isCancelled()) {
            return false;
        }

        this.prisonersRepository.deletePrisoner(player.getUniqueId());
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

                this.jailedPlayers.remove(uuid);

                if (jailedPlayer != null) {
                    this.teleportService.teleport(jailedPlayer, this.spawnService.getSpawnLocation());
                }
            }
        );

        this.jailedPlayers.clear();
        this.prisonersRepository.deleteAllPrisoners();
    }

    @Override
    public boolean isPlayerJailed(UUID player) {
        if (!this.jailedPlayers.containsKey(player)) {
            return false;
        }

        return !this.jailedPlayers.get(player).isReleased();
    }

    @Override
    public boolean isCommandAllowed(String command) {
        return this.settings.allowedCommands().contains(command);
    }

    @Override
    public Collection<Prisoner> getPrisoners() {
        return Collections.unmodifiableCollection(this.jailedPlayers.values());
    }

    private void loadFromDatabase() {
        this.prisonersRepository.getPrisoners().thenAccept(prisoners -> {
            for (Prisoner prisoner : prisoners) {
                this.jailedPlayers.put(prisoner.getPlayerUniqueId(), prisoner);
            }
        });
    }
}
