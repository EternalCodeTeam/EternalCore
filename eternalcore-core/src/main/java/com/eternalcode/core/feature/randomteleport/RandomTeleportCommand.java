package com.eternalcode.core.feature.randomteleport;

import static com.eternalcode.core.feature.randomteleport.RandomTeleportPermissionConstant.RTP_BYPASS_PERMISSION;
import static com.eternalcode.core.feature.randomteleport.RandomTeleportPlaceholders.PLACEHOLDERS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "rtp", aliases = "randomteleport")
class RandomTeleportCommand {

    private final NoticeService noticeService;
    private final RandomTeleportService randomTeleportService;
    private final PluginConfiguration config;
    private final Delay<UUID> delay;

    @Inject
    RandomTeleportCommand(
        NoticeService noticeService,
        RandomTeleportService randomTeleportService,
        PluginConfiguration config
    ) {
        this.noticeService = noticeService;
        this.randomTeleportService = randomTeleportService;
        this.config = config;
        this.delay = new Delay<>(this.config.randomTeleport);
    }

    @Execute
    @Permission("eternalcore.rtp")
    @DescriptionDocs(description = "Teleportation of the sender to a random location, if you want bypass cooldown use eternalcore.rtp.bypass permission")
    void executeSelf(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRandomTeleportDelay(player)) {
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

            });

        this.delay.markDelay(uuid, this.config.randomTeleport.delay());
    }

    @Execute
    @Permission("eternalcore.rtp.other")
    @DescriptionDocs(description = "Teleportation of a player to a random location.", arguments = "<player>")
    void executeOther(@Context Viewer sender, @Arg Player player) {
        UUID uuid = player.getUniqueId();

        if (this.hasRandomTeleportDelay(player)) {
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

    private void handleAdminTeleport(Viewer sender, Player player) {
        this.noticeService.viewer(
            sender,
            translation -> translation.randomTeleport().teleportedSpecifiedPlayerToRandomLocation(),
            PLACEHOLDERS.toFormatter(player));
    }

    private boolean hasRandomTeleportDelay(Player player) {
        UUID uniqueId = player.getUniqueId();

        if (player.hasPermission(RTP_BYPASS_PERMISSION) || player.isOp()) {
            return false;
        }

        if (this.delay.hasDelay(uniqueId)) {
            Duration time = this.delay.getDurationToExpire(uniqueId);

            this.noticeService.create()
                .notice(translation -> translation.randomTeleport().randomTeleportDelay())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(uniqueId)
                .send();

            return true;
        }

        return false;
    }
}
