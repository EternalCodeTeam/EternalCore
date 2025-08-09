package com.eternalcode.core.feature.serverlinks;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ServerLinksConfig extends OkaeriConfig implements ServerLinksSettings {

    @Comment({
        "# Configuration of server links displayed in the ESC/pause menu",
        "# Links will be visible in the game's pause menu under server information",
        "# Note: This feature requires Minecraft version 1.21 or newer to work properly"
    })
    public boolean sendLinksOnJoin = true;

    public List<ServerLinksEntry> serverLinks = List.of(
        ServerLinksEntry.of("<rainbow>Discord", "https://discord.gg/v2rkPb4Q2r"),
        ServerLinksEntry.of("Website", "https://www.eternalcode.pl")
    );
}
