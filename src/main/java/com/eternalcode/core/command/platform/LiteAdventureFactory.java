package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.AudienceProvider;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteFactory;
import dev.rollczi.litecommands.bind.basic.OriginalSenderBind;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public final class LiteAdventureFactory {

    private LiteAdventureFactory() {}

    public static LiteCommandsBuilder<CommandSender, LiteAdventurePlatform> builder(Server server, String fallbackPrefix, AudienceProvider audienceProvider) {
        return LiteFactory.<CommandSender, LiteAdventurePlatform>builder()
            .bind(Server.class, server)
            .bind(CommandSender.class, new OriginalSenderBind())
            .platform(new LiteAdventurePlatform(server, fallbackPrefix, audienceProvider));
    }

}
