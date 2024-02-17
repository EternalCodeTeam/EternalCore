package com.eternalcode.core.feature.jail;

import com.eternalcode.core.feature.jail.event.JailDetainEvent;
import com.eternalcode.core.feature.jail.event.JailReleaseEvent;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import lombok.Getter;
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

    // id - name - location
    // 1 - "" - jail location

    private final TeleportService teleportService;
    private final SpawnService spawnService;
    private final NoticeService noticeService;

    private Location jailLocation;


    private final Map<UUID, Prisoner> jailedPlayers = new ConcurrentHashMap<>();


    @Inject
    JailServiceImpl(TeleportService teleportService, SpawnService spawnService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.spawnService = spawnService;
        this.noticeService = noticeService;
    }

    @Override
    public Map<UUID, Prisoner> getJailedPlayers() {
        return this.jailedPlayers;
    }

    @Override
    public Location getJailLocation() {
        return this.jailLocation;
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

        this.jailLocation = jailLocation;
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

        this.jailLocation = null;
        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailLocationRemove())
            .player(remover.getUniqueId())
            .send();
    }

    @Override
    public void detainPlayer(Player player, @Nullable String reason, Player detainedBy, Duration time) {
        if (!this.isLocationSet()) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailLocationNotSet())
                .player(player.getUniqueId())
                .send();
            return;
        }

        if (!player.isOnline()) {
            this.noticeService.create()
                .notice(translation -> translation.argument().offlinePlayer())
                .player(detainedBy.getUniqueId())
                .send();
        }

        if (this.jailedPlayers.containsKey(player.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainOverride())
                .player(player.getUniqueId())
                .send();
        }

        JailDetainEvent jailDetainEvent = new JailDetainEvent(player, reason, detainedBy);

        if (jailDetainEvent.isCancelled()) {
            return;
        }

        this.jailedPlayers.put(player.getUniqueId(), new Prisoner(player.getUniqueId(), Instant.now(), reason, detainedBy.getUniqueId()));

        if (player.getUniqueId().equals(detainedBy.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailDetainPrivate())
                .player(player.getUniqueId())
                .send();
        }


        this.teleportService.teleport(player, this.jailLocation);
        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailDetainPublic())
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
        // Detain the player
    }

    @Override
    public void releasePlayer(Player player, Player releasedBy) {

        if (this.jailedPlayers.containsKey(player.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.jailSection().jailReleaseNoPlayer())
                .player(releasedBy.getUniqueId())
                .send();
            return;
        }

        JailReleaseEvent jailReleaseEvent = new JailReleaseEvent(player.getUniqueId());

        if (jailReleaseEvent.isCancelled()) {
            return;
        }

        this.jailedPlayers.remove(player.getUniqueId());
        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleaseSender())
            .placeholder("{PLAYER}", player.getName())
            .player(releasedBy.getUniqueId())
            .send();

        this.teleportService.teleport(player, this.spawnService.getSpawnLocation());
        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleasePrivate())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleasePublic())
            .placeholder("{PLAYER}", player.getName())
            .all()
            .send();
        // Release the player
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
        this.noticeService.create()
            .notice(translation -> translation.jailSection().jailReleaseAll())
            .all()
            .send();
    }

    @Override
    public boolean isLocationSet() {

        return this.jailLocation != null;
    }

}
