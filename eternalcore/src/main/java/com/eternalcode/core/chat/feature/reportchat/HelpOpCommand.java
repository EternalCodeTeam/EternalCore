package com.eternalcode.core.chat.feature.reportchat;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.notification.Notice;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

// TODO: Refactor
@Route(name = "helpop", aliases = {"report"})
@Permission("eternalcore.helpop")
public class HelpOpCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;
    private final Delay<UUID> delay;

    public HelpOpCommand(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;
        this.delay = new Delay<>(this.config, this.config.chat);
    }

    @Execute
    @Min(1)
    void execute(Player player, @Joiner @Name("message") String text) {
        UUID uuid = player.getUniqueId();

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.helpOp().helpOpDelay())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(player.getUniqueId())
                .send();

            return;
        }

        Notice notice = this.noticeService.create()
            .console()
            .player(player.getUniqueId())
            .notice(translation -> translation.helpOp().format())
            .placeholder("{NICK}", player.getName())
            .placeholder("{TEXT}", text);

        for (Player admin : this.server.getOnlinePlayers()) {
            if (!admin.hasPermission("eternalcore.helpop.spy")) {
                continue;
            }

            notice = notice.player(admin.getUniqueId());
        }

        notice.send();

        this.noticeService
            .create()
            .notice(translation -> translation.helpOp().send())
            .send();

        this.delay.markDelay(uuid, this.config.chat.helpOpDelay);
    }

}
