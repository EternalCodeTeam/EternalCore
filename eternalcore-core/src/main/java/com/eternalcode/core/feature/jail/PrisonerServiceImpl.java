package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    private final Map<UUID, Prisoner> jailedPlayers = new ConcurrentHashMap<>();
    private final TeleportService teleportService;
    private final NoticeService noticeService;
    private final SpawnService spawnService;
    private final JailService jailService;
    private final JailSettings settings;
    private final Server server;

    private final PrisonersRepository prisonersRepository;

    @Inject
    public PrisonerServiceImpl(TeleportService teleportService, NoticeService noticeService, SpawnService spawnService, JailService jailService, JailSettings settings, Server server, PrisonersRepository prisonersRepository) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
        this.spawnService = spawnService;
        this.jailService = jailService;
        this.settings = settings;
        this.server = server;
        this.prisonersRepository = prisonersRepository;

        this.loadFromDatabase();
    }

    @Override
    public Map<UUID, Prisoner> getJailedPlayers() {
        return this.jailedPlayers;
    }

    @Override
    public void detainPlayer(Player player, Player detainedBy, @Nullable Duration time) {
        if (!this.jailService.isLocationSet()) {
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
                .placeholder("{PLAYER}", player.getName())
                .player(player.getUniqueId())
                .send();
        }


        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, detainedBy);

        if (jailDetainEvent.isCancelled()) {
            return;
        }

        Prisoner prisoner = new Prisoner(player.getUniqueId(), Instant.now(), time, detainedBy.getUniqueId());

        this.prisonersRepository.savePrisoner(prisoner);

        this.jailedPlayers.put(player.getUniqueId(), prisoner);

        this.teleportService.teleport(player, this.jailService.getJailPosition());

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainPublic())
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainPrivate())
            .player(player.getUniqueId())
            .send();

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
                Player jailedPlayer = this.server.getPlayer(uuid);

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
            Player jailedPlayer = this.server.getPlayer(uuid);

            if (jailedPlayer != null) {
                this.noticeService.create()
                    .notice(translation -> translation.jailSection().jailListPlayer())
                    .placeholder("{PLAYER}", jailedPlayer.getName())
                    .placeholder("{DURATION}", DurationUtil.format(prisoner.getReleaseTime()))
                    .placeholder("{DETAINED_BY}", this.server.getOfflinePlayer(prisoner.getDetainedBy()).getName())
                    .player(player.getUniqueId())
                    .send();
            }
        });
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
