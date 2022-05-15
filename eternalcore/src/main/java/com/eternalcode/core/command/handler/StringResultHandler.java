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

public class StringResultHandler implements Handler<CommandSender, String> {

    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public StringResultHandler(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, String value) {
        Component deserialized = miniMessage.deserialize(value);

        if (sender instanceof Player player) {
            this.audienceProvider.player(player.getUniqueId()).sendMessage(deserialized);
            return;
        }

        if (sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender || sender instanceof BlockCommandSender) {
            this.audienceProvider.console().sendMessage(deserialized);
        }
    }

}
