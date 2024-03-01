package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.commons.time.DurationParser;
import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import com.eternalcode.core.notice.NoticeService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JailServiceImpl implements JailService {

    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final NoticeService noticeService;
    private final JailSettings settings;
    private Position jailPosition;
    private final JailLocationRepository jailRepository;

    private final PrisonersRepository prisonersRepository;


    private final Map<UUID, Prisoner> jailedPlayers = new ConcurrentHashMap<>();


    @Inject
    JailServiceImpl(TeleportService teleportService, SpawnService spawnService, NoticeService noticeService, PrisonersRepository prisonersRepository, JailLocationRepository jailLocationRepository, JailSettings settings) {
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.noticeService = noticeService;
        this.settings = settings;
        this.prisonersRepository = prisonersRepository;
        this.jailRepository = jailLocationRepository;

        this.prisonersRepository.getPrisoners().thenApply(prisoners -> {
            for (Prisoner prisoner : prisoners) {
                this.jailedPlayers.put(prisoner.getUuid(), prisoner);
            }
            return null;
        });

        this.jailPosition = this.jailRepository.getJailLocation().join().orElse(null);
    }

    @Override
    public Map<UUID, Prisoner> getJailedPlayers() {
        return this.jailedPlayers;
    }

    @Override
    public Location getJailPosition() {
        return PositionAdapter.convert(this.jailPosition);
    }

    @Override
    public void setupJailArea(Location jailLocation, Player setter) {
        if (this.isLocationSet()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationOverride())
                .player(setter.getUniqueId())
                .send();
        }
        else {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationSet())
                .player(setter.getUniqueId())
                .send();
        }

        Position position = new Position(jailLocation.getX(), jailLocation.getY(), jailLocation.getZ(), jailLocation.getYaw(), jailLocation.getPitch(), setter.getWorld().getName());

        this.jailRepository.setJailLocation(position);
        this.jailPosition = position;
    }

    @Override
    public void removeJailArea(Player remover) {
        if (!this.isLocationSet()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationNotSet())
                .player(remover.getUniqueId())
                .send();
            return;
        }

        this.jailPosition = null;
        this.jailRepository.deleteJailLocation();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailLocationRemove())
            .player(remover.getUniqueId())
            .send();
    }

    @Override
    public void detainPlayer(Player player, Player detainedBy, @Nullable Duration time) {
        if (!this.isLocationSet()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationNotSet())
                .player(player.getUniqueId())
                .send();
            return;
        }

        if (time == null) {
            time = this.settings.defaultJailDuration();
        }

        if (!player.isOnline()) {
            this.noticeService.create()
                .notice(translation -> translation.argument().offlinePlayer())
                .player(detainedBy.getUniqueId())
                .send();
        }

        boolean isPlayerJailed = this.jailedPlayers.containsKey(player.getUniqueId());

        if (isPlayerJailed) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainOverride())
                .player(player.getUniqueId())
                .send();
        }


        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, detainedBy);

        if (jailDetainEvent.isCancelled()) {
            return;
        }

        Prisoner prisoner = new Prisoner(player.getUniqueId(), Instant.now(), time, detainedBy.getUniqueId());

        if (isPlayerJailed) {
            this.prisonersRepository.editPrisoner(prisoner);
        }
        else {
            this.prisonersRepository.savePrisoner(prisoner);
        }

        this.jailedPlayers.put(player.getUniqueId(), prisoner);

        if (player.getUniqueId().equals(detainedBy.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainPrivate())
                .player(player.getUniqueId())
                .send();
        }

        Location jailLocation = PositionAdapter.convert(this.jailPosition);

        this.teleportService.teleport(player, jailLocation);

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainPublic())
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
        // Detain the player
    }


    @Override
    public void releasePlayer(Player player, @Nullable Player releasedBy) {

        if (releasedBy != null) {
            if (!this.jailedPlayers.containsKey(player.getUniqueId())) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailReleaseNoPlayer())
                    .player(releasedBy.getUniqueId())
                    .send();
                return;
            }

            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailReleaseSender())
                .placeholder("{PLAYER}", player.getName())
                .player(releasedBy.getUniqueId())
                .send();
        }

        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player.getUniqueId());

        if (jailReleaseEvent.isCancelled()) {
            return;
        }

        this.prisonersRepository.deletePrisoner(player.getUniqueId());
        this.jailedPlayers.remove(player.getUniqueId());

        this.spawnService.teleportToSpawn(player);

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleasePrivate())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleasePublic())
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();

    }

    @Override
    public void releaseAllPlayers(Player player) {

        if (this.jailedPlayers.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailReleaseNoPlayers())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.jailedPlayers.forEach((uuid, prisoner) -> {
                Player jailedPlayer = Bukkit.getPlayer(uuid);

                JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(uuid);

                if (jailReleaseEvent.isCancelled()) {
                    return;
                }
                this.jailedPlayers.remove(uuid);

                if (jailedPlayer != null) {
                    this.teleportService.teleport(jailedPlayer, this.spawnService.getSpawnLocation());
                    this.noticeService.create()
                        .notice(translation -> translation.jailSection().jailReleasePrivate())
                        .player(jailedPlayer.getUniqueId())
                        .send();

                    this.noticeService.create()
                        .notice(translation -> translation.jailSection().jailReleasePublic())
                        .placeholder("{PLAYER}", jailedPlayer.getName())
                        .all()
                        .send();

                    this.teleportService.teleport(jailedPlayer, this.spawnService.getSpawnLocation());
                }
            }
        );

        this.jailedPlayers.clear();
        this.prisonersRepository.deleteAllPrisoners();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleaseAll())
            .all()
            .send();
    }

    @Override
    public void listJailedPlayers(Player player) {
        if (this.jailedPlayers.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailListNoPlayers())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailListStart())
            .player(player.getUniqueId())
            .send();

        this.jailedPlayers.forEach((uuid, prisoner) -> {
            Player jailedPlayer = Bukkit.getPlayer(uuid);

            if (jailedPlayer != null) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailListPlayer())
                    .placeholder("{PLAYER}", jailedPlayer.getName())
                    .placeholder("{DURATION}", DurationParser.DATE_TIME_UNITS.format(prisoner.getReleaseTime()))
                    .placeholder("{DETAINED_BY}", Bukkit.getOfflinePlayer(prisoner.getDetainedBy()).getName())
                    .player(player.getUniqueId())
                    .send();
            }
        });
    }

    @Override
    public boolean isLocationSet() {
        return this.jailPosition != null;
    }

    @Override
    public boolean isAllowedCommand(String command) {
        return this.settings.allowedCommands().contains(command);
    }

    @Override
    public boolean isPlayerJailed(UUID player) {
        if (!this.jailedPlayers.containsKey(player)) {
            return false;
        }

        return !this.jailedPlayers.get(player).isReleased();
    }
}
