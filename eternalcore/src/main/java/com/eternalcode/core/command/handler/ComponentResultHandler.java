package com.eternalcode.core.command.handler;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class ComponentResultHandler implements Handler<CommandSender, Component> {

    private final AudienceProvider audienceProvider;

    public ComponentResultHandler(AudienceProvider audienceProvider) {
        this.audienceProvider = audienceProvider;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Component value) {
        if (sender instanceof Player player) {
            this.audienceProvider.player(player.getUniqueId()).sendMessage(value);
            return;
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            this.audienceProvider.console().sendMessage(value);
        }
    }

}
