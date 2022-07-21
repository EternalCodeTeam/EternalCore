package com.eternalcode.core.chat.feature.reportchat;

import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
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

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

//TODO: Refactor
@Section(route = "helpop", aliases = { "report" })
@Permission("eternalcore.helpop")
public class HelpOpCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Cache<UUID, Instant> cooldowns; // TODO: <- nie trzymać tego w takim miejsu
    private final Server server;

    public HelpOpCommand(NoticeService noticeService, PluginConfiguration config, Server server) {
        this.noticeService = noticeService;
        this.config = config;
        this.server = server;

        this.cooldowns = CacheBuilder.newBuilder()
            .expireAfterWrite(this.config.chat.helpOpDelay.plus(Duration.ofSeconds(1)))
            .build();
    }

    @Execute
    @Min(1)
    void execute(Player player, @Joiner String text) {
        UUID uuid = player.getUniqueId();
        Instant unblockMoment = this.cooldowns.asMap().getOrDefault(uuid, Instant.MIN);

        if (Instant.now().isBefore(unblockMoment)) {
            Duration time = Duration.between(Instant.now(), unblockMoment);

            this.noticeService
                .create()
                .message(messages -> messages.helpOp().coolDown())
                .placeholder("{TIME}", DurationUtil.format(time))
                .player(player.getUniqueId())
                .send();

            return;
        }

        Notice notice = this.noticeService.create()
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
            .create()
            .message(messages -> messages.helpOp().send())
            .send();

        this.cooldowns.put(uuid, Instant.now().plus(this.config.chat.helpOpDelay));
    }
}
