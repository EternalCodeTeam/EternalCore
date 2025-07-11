package com.eternalcode.core.feature.afk;

import static com.eternalcode.core.feature.afk.AfkCommand.AFK_BYPASS_PERMISSION;

import com.eternalcode.annotations.scan.command.DescriptionDocs;

import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "afk")
@Permission("eternalcore.afk")
@PermissionDocs(
    name = "Afk bypass",
    permission = AFK_BYPASS_PERMISSION,
    description = "Allows player to bypass AFK delay when using /afk command."
)
class AfkCommand {

    static final String AFK_BYPASS_PERMISSION = "eternalcore.afk.bypass";

    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;
    private final AfkService afkService;
    private final Delay<UUID> delay;

    @Inject
    AfkCommand(NoticeService noticeService, PluginConfiguration pluginConfiguration, AfkService afkService) {
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
        this.afkService = afkService;
        this.delay = new Delay<>(() -> this.pluginConfiguration.afk.getAfkDelay());
    }

    @Execute
    @DescriptionDocs(description = "Toggles AFK status for the player.")
    void execute(@Context Player player) {
        UUID uuid = player.getUniqueId();

        if (player.hasPermission(AFK_BYPASS_PERMISSION)) {
            this.afkService.switchAfk(uuid, AfkReason.COMMAND);
            return;
        }

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.afk().afkDelay())
                .placeholder("{TIME}", DurationUtil.format(time, true))
                .player(uuid)
                .send();

            return;
        }

        this.afkService.switchAfk(uuid, AfkReason.COMMAND);
        this.delay.markDelay(uuid, this.pluginConfiguration.afk.getAfkDelay());
    }
}
