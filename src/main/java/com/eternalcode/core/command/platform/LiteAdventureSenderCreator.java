package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.audience.Audience;
import com.eternalcode.core.chat.audience.AudienceProvider;
import dev.rollczi.litecommands.platform.LiteSender;
import dev.rollczi.litecommands.platform.LiteSenderCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LiteAdventureSenderCreator implements LiteSenderCreator<CommandSender> {

    private final AudienceProvider audienceProvider;

    public LiteAdventureSenderCreator(AudienceProvider audienceProvider) {
        this.audienceProvider = audienceProvider;
    }

    @Override
    public LiteSender create(CommandSender sender) {
        if (!(sender instanceof ConsoleCommandSender) && !(sender instanceof Player)) {
            throw new IllegalStateException();
        }

        Audience audience = sender instanceof Player player
            ? audienceProvider.player(player.getUniqueId())
            : audienceProvider.console();

        return new LiteAdventureSender(sender, audience);
    }

}
