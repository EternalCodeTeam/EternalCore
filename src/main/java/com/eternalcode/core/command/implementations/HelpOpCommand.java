/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.DateUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Section(route = "helpop", aliases = { "report" })
@Permission("eternalcore.command.helpop")
@UsageMessage("&8» &cPoprawne użycie &7/helpop <text>")
public class HelpOpCommand {

    private final MessagesConfiguration messages;
    private final PluginConfiguration config;
    private final Cache<UUID, Long> cooldowns;
    private final Server server;

    public HelpOpCommand(MessagesConfiguration messages, PluginConfiguration config, Server server) {
        this.messages = messages;
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

            player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.helpOpSection.cooldown, "{TIME}", DateUtils.durationToString(time))));
            return;
        }

        this.server.getOnlinePlayers()
            .stream()
            .filter(players -> players.hasPermission("eternalcore.helpop.spy") || players.isOp())
            .forEach(players -> players.sendMessage(
                ChatUtils.color(StringUtils.replaceEach(this.messages.helpOpSection.format,
                    new String[]{ "{NICK}", "{TEXT}" },
                    new String[]{ player.getName(), text }))));

        player.sendMessage(ChatUtils.color(this.messages.helpOpSection.send));

        this.cooldowns.put(uuid, (long) (System.currentTimeMillis() + this.config.chat.helpopCooldown * 1000L));
    }
}
