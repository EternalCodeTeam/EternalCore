package com.eternalcode.core.feature.helpop;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.helpop.event.HelpOpEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Command(name = "helpop", aliases = { "report" })
@Permission("eternalcore.helpop")
@PermissionDocs(
    name = "HelpOp Spy",
    description = "Allows player to see helpop messages sent by other players",
    permission = HelpOpCommand.HELPOP_SPY
)
class HelpOpCommand {

    static final String HELPOP_SPY = "eternalcore.helpop.spy";

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final EventCaller eventCaller;
    private final Server server;
    private final Delay<UUID> delay;

    @Inject
    HelpOpCommand(NoticeService noticeService, PluginConfiguration config, EventCaller eventCaller, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.eventCaller = eventCaller;
        this.server = server;
        this.delay = new Delay<>(() -> this.config.helpOp.getHelpOpDelay());
    }

    @Execute
    @DescriptionDocs(description = "Send helpop message to all administrator with eternalcore.helpop.spy permission", arguments = "<message>")
    void execute(@Context Player player, @Join String message) {
        UUID uuid = player.getUniqueId();
        HelpOpEvent event = new HelpOpEvent(player, message);

        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getDurationToExpire(uuid);

            this.noticeService.create()
                .notice(translation -> translation.helpOp().helpOpDelay())
                .placeholder("{TIME}", DurationUtil.format(time, true))
                .player(uuid)
                .send();

            return;
        }

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.helpOp().format())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{TEXT}", message);

        for (Player admin : this.server.getOnlinePlayers()) {
            if (!admin.hasPermission(HELPOP_SPY)) {
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

        this.delay.markDelay(uuid, this.config.helpOp.getHelpOpDelay());
    }

}
