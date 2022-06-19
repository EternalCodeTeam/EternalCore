package com.eternalcode.core.chat.feature.reportchat;

import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.util.DurationUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

//TODO: Refactor
@Section(route = "helpop", aliases = { "report" })
@Permission("eternalcore.helpop")
public class HelpOpCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Cache<UUID, Long> cooldowns; // TODO: <- nie trzymać tego w takim miejsu
    private final Server server;

    public HelpOpCommand(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;

        this.cooldowns = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.config.chat.helpopCooldown + 10), TimeUnit.SECONDS)
            .build();
    }

    @Execute
    @Min(1)
    public void execute(Player player, @Joiner String text) {
        UUID uuid = player.getUniqueId();

        if (this.cooldowns.asMap().getOrDefault(uuid, 0L) > System.currentTimeMillis()) {
            long time = Math.max(this.cooldowns.asMap().getOrDefault(uuid, 0L) - System.currentTimeMillis(), 0L);

            this.noticeService
                .notice()
                .message(messages -> messages.helpOp().coolDown())
                .placeholder("{TIME}", DurationUtil.durationToString(time))
                .player(player.getUniqueId())
                .send();

            return;
        }

        Notice notice = this.noticeService.notice()
            .console()
            .player(player.getUniqueId())
            .message(messages -> messages.helpOp().format())
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
            .notice()
            .message(messages -> messages.helpOp().send())
            .send();

        this.cooldowns.put(uuid, (long) (System.currentTimeMillis() + this.config.chat.helpopCooldown * 1000L));
    }
}
