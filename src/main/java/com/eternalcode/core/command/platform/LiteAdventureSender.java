package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.audience.Audience;
import dev.rollczi.litecommands.platform.LiteSender;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class LiteAdventureSender implements LiteSender {

    private final MiniMessage miniMessage = MiniMessage.get();
    private final CommandSender commandSender;
    private final Audience audience;

    public LiteAdventureSender(CommandSender commandSender, Audience audience) {
        this.audience = audience;
        this.commandSender = commandSender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        this.audience.message(miniMessage.parse(message));
    }

    @Override
    public CommandSender getSender() {
        return commandSender;
    }

}
