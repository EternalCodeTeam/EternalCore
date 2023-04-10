package com.eternalcode.core.feature.afk;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Route(name = "afk")
@Permission("eternalcore.afk")
public class AfkCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;
    private final AfkService afkService;
    private final Delay<UUID> delay;

    public AfkCommand(NoticeService noticeService, PluginConfiguration pluginConfiguration, AfkService afkService) {
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
        this.afkService = afkService;
        this.delay = new Delay<>(this.pluginConfiguration.afk);
    }

    @Execute
    void execute(Player player) {
        UUID uuid = player.getUniqueId();

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.afk().afkDelay())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(uuid)
                .send();

            return;
        }

        this.afkService.markAfk(uuid, AfkReason.COMMAND);

        if (player.hasPermission("eternalcore.afk.bypass")) {
            return;
        }

        this.delay.markDelay(uuid, this.pluginConfiguration.afk.delay());
    }

}
