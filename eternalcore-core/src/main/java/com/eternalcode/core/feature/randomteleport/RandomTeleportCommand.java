package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.UUID;

@Command(name = "rtp", aliases = "randomteleport")
class RandomTeleportCommand {

    private static final Placeholders<Player> PLACEHOLDERS = Placeholders.<Player>builder()
        .with("{PLAYER}", Player::getName)
        .with("{WORLD}", player -> player.getWorld().getName())
        .with("{X}", player -> String.valueOf(player.getLocation().getBlockX()))
        .with("{Y}", player -> String.valueOf(player.getLocation().getBlockY()))
        .with("{Z}", player -> String.valueOf(player.getLocation().getBlockZ()))
        .build();

    private final NoticeService noticeService;
    private final RandomTeleportService randomTeleportService;
    private final PluginConfiguration config;
    private final Delay<UUID> delay;

    @Inject
    RandomTeleportCommand(NoticeService noticeService, RandomTeleportService randomTeleportService, PluginConfiguration config) {
        this.noticeService = noticeService;
        this.randomTeleportService = randomTeleportService;
        this.config = config;
        this.delay = new Delay<>(this.config.randomTeleport);
    }

    @Execute
    @Permission("eternalcore.rtp")
    @DescriptionDocs(description = "Teleportation of the sender to a random location.")
    void executeSelf(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRTPDelay(uuid)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportStarted())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player)
            .whenCompleteAsync((result, error) -> {
                if (error != null || !result.success()) {
                    this.handleTeleportFailure(player);
                    return;
                }

                this.handleTeleportSuccess(player);
            });

        this.delay.markDelay(uuid, this.config.randomTeleport.delay());
    }

    @Execute
    @Permission("eternalcore.rtp.other")
    @DescriptionDocs(description = "Teleportation of a player to a random location.", arguments = "<player>")
    void executeOther(@Context Viewer sender, @Arg Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRTPDelay(uuid)) {
            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportStarted())
            .player(player.getUniqueId())
            .send();

        this.randomTeleportService.teleport(player)
            .whenCompleteAsync((result, error) -> {
                if (error != null || !result.success()) {
                    this.handleTeleportFailure(player);
                    return;
                }

                this.handleTeleportSuccess(player);
                this.handleAdminTeleport(sender, player);
            });

        this.delay.markDelay(uuid, this.config.randomTeleport.delay());
    }

    private void handleTeleportFailure(Player player) {
        this.noticeService.create()
            .notice(translation -> translation.randomTeleport().randomTeleportFailed())
            .player(player.getUniqueId())
            .send();
    }

    private void handleTeleportSuccess(Player player) {
        this.noticeService.player(player.getUniqueId(),
            translation -> translation.randomTeleport().teleportedToRandomLocation(),
            PLACEHOLDERS.toFormatter(player));
    }

    private void handleAdminTeleport(Viewer sender, Player player) {
        this.noticeService.viewer(sender,
            translation -> translation.randomTeleport().teleportedSpecifiedPlayerToRandomLocation(),
            PLACEHOLDERS.toFormatter(player));
    }


    private boolean hasRTPDelay(UUID uuid) {
        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.randomTeleport().randomTeleportDelay())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(uuid)
                .send();

            return true;
        }
        return false;
    }
}
