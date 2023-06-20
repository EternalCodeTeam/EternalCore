package com.eternalcode.core.afk;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Route(name = "afk")
@Permission("eternalcore.afk")
@FeatureDocs(
    name = "Afk",
    permission = { "eternalcore.afk", "eternalcore.afk.bypass" },
    description = "It allows you to mark yourself as AFK, or if you are AFK, eternalcore will mark you as AFK after some time"
)
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
    @DescriptionDocs(description = "Marks you as AFK, if player has eternalcore.afk.bypass permission, eternalcore will be ignore afk delay")
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

        this.afkService.switchAfk(uuid, AfkReason.COMMAND);

        if (player.hasPermission("eternalcore.afk.bypass")) {
            return;
        }

        this.delay.markDelay(uuid, this.pluginConfiguration.afk.delay());
    }

}
