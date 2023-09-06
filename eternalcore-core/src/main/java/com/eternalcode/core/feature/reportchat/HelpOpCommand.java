package com.eternalcode.core.feature.reportchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Route(name = "helpop", aliases = { "report" })
@Permission("eternalcore.helpop")
public class HelpOpCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;
    private final Delay<UUID> delay;

    @Inject
    public HelpOpCommand(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;
        this.delay = new Delay<>(this.config.chat);
    }

    @Execute
    @Min(1)
    @DescriptionDocs(description = "Send helpop message to all administrator with eternalcore.helpop.spy permission", arguments = "<message>")
    void execute(Player player, @Joiner String message) {
        UUID uuid = player.getUniqueId();

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.helpOp().helpOpDelay())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(uuid)
                .send();

            return;
        }

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .player(uuid)
            .notice(translation -> translation.helpOp().format())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{TEXT}", message);

        for (Player admin : this.server.getOnlinePlayers()) {
            if (!admin.hasPermission("eternalcore.helpop.spy")) {
                continue;
            }

            notice = notice.player(admin.getUniqueId());
        }

        notice.send();

        this.noticeService
            .create()
            .player(player.getUniqueId())
            .notice(translation -> translation.helpOp().send())
            .send();

        this.delay.markDelay(uuid, this.config.chat.helpOpDelay);
    }

}
