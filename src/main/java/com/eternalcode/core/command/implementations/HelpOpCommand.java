/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.DateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Section(route = "helpop", aliases = { "report" })
@Permission("eternalcore.command.helpop")
@UsageMessage("&8» &cPoprawne użycie &7/helpop <text>")
public class HelpOpCommand {

    private final AudiencesService audiencesService;
    private final PluginConfiguration config;
    private final Cache<UUID, Long> cooldowns;
    private final Server server;

    public HelpOpCommand(AudiencesService audiencesService, PluginConfiguration config, Server server) {
        this.audiencesService = audiencesService;
        this.config = config;
        this.server = server;

        this.cooldowns = CacheBuilder.newBuilder()
            .expireAfterWrite((long) (this.config.chat.helpopCooldown + 10), TimeUnit.SECONDS)
            .build();
    }

    @Execute
    @MinArgs(1)
    public void execute(Player player, @Joiner String text) {
        UUID uuid = player.getUniqueId();

        if (this.cooldowns.asMap().getOrDefault(uuid, 0L) > System.currentTimeMillis()) {
            long time = Math.max(this.cooldowns.asMap().getOrDefault(uuid, 0L) - System.currentTimeMillis(), 0L);

            this.audiencesService
                .notice()
                .message(messages -> messages.helpOp().coolDown())
                .placeholder("{TIME}", DateUtils.durationToString(time))
                .player(player.getUniqueId())
                .send();

            return;
        }

        Notice notice = audiencesService.notice()
            .console()
            .message(messages -> messages.helpOp().format())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{TEXT}", text);

        for (Player admin : this.server.getOnlinePlayers()) {
            if (!admin.hasPermission("eternalcore.helpop.spy")) {
                continue;
            }

            notice.player(admin.getUniqueId());
        }

        notice.send();

        this.audiencesService
            .notice()
            .message(messages -> messages.helpOp().send())
            .send();

        this.cooldowns.put(uuid, (long) (System.currentTimeMillis() + this.config.chat.helpopCooldown * 1000L));
    }
}
