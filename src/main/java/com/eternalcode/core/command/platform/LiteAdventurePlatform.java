package com.eternalcode.core.command.platform;

import com.eternalcode.core.chat.notification.AudienceProvider;
import dev.rollczi.litecommands.bukkit.LiteBukkitPlatformManager;
import dev.rollczi.litecommands.platform.LitePlatformManager;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class LiteAdventurePlatform extends LiteBukkitPlatformManager implements LitePlatformManager<CommandSender> {

    protected LiteAdventurePlatform(Server server, String fallbackPrefix, AudienceProvider audienceProvider) {
        super(server, fallbackPrefix);

        this.setLiteSenderCreator(new LiteAdventureSenderCreator(audienceProvider));
    }

}
