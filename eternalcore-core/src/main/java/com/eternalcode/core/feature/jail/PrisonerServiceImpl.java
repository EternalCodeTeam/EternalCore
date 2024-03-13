package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    private final Map<UUID, Prisoner> jailedPlayers = new ConcurrentHashMap<>();
    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final JailService jailService;
    private final JailSettings settings;
    private final Server server;

    private final PrisonersRepository prisonersRepository;

    @Inject
    public PrisonerServiceImpl(TeleportService teleportService, SpawnService spawnService, JailService jailService, JailSettings settings, Server server, PrisonersRepository prisonersRepository) {
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.jailService = jailService;
        this.settings = settings;
        this.server = server;
        this.prisonersRepository = prisonersRepository;

        this.loadFromDatabase();
    }

    @Override
    public Map<UUID, Prisoner> getPrisoners() {
        return this.jailedPlayers;
    }

    @Override
    public boolean isPrisoner(UUID player) {
        return this.jailedPlayers.containsKey(player);
    }

    @Override
    public void detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration time) {
        if (time == null) {
            time = this.settings.defaultJailDuration();
        }

        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, detainedBy);

        if (jailDetainEvent.isCancelled()) {
            return;
        }

        Prisoner prisoner = new Prisoner(player.getUniqueId(), Instant.now(), time, detainedBy.getName());

        this.prisonersRepository.savePrisoner(prisoner);
        this.jailedPlayers.put(player.getUniqueId(), prisoner);

        this.teleportService.teleport(player, this.jailService.getJailPosition());
    }

    @Override
    public void releasePlayer(Player player, @Nullable CommandSender releasedBy) {
        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player.getUniqueId());

        if (jailReleaseEvent.isCancelled()) {
            return;
        }

        this.prisonersRepository.deletePrisoner(player.getUniqueId());
        this.jailedPlayers.remove(player.getUniqueId());

        this.spawnService.teleportToSpawn(player);
    }

    @Override
    public void releaseAllPlayers() {
        this.jailedPlayers.forEach((uuid, prisoner) -> {
                Player jailedPlayer = this.server.getPlayer(uuid);

                JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(uuid);

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
    public Set<JailedPlayer> getJailedPlayers() {

        Set<JailedPlayer> jailedPlayersSet = new HashSet<>();

        this.jailedPlayers.forEach((uuid, prisoner) -> {
            Player jailedPlayer = this.server.getPlayer(uuid);

            if (jailedPlayer != null) {

                jailedPlayersSet.add(new JailedPlayer(
                    jailedPlayer.getName(),
                    DurationUtil.format(prisoner.getReleaseTime()),
                    prisoner.getDetainedBy()
                ));
            }
        });

        return jailedPlayersSet;
    }

    @Override
    public boolean isPlayerJailed(UUID player) {
        if (!this.jailedPlayers.containsKey(player)) {
            return false;
        }

        return !this.jailedPlayers.get(player).isReleased();
    }

    @Override
    public boolean isAllowedCommand(String command) {
        return this.settings.allowedCommands().contains(command);
    }

    private void loadFromDatabase() {
        this.prisonersRepository.getPrisoners().thenAccept(prisoners -> {
            for (Prisoner prisoner : prisoners) {
                this.jailedPlayers.put(prisoner.getUuid(), prisoner);
            }
        });
    }

}
