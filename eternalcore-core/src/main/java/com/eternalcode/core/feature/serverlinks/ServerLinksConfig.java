package com.eternalcode.core.feature.serverlinks;

import java.util.List;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Contextual
public class ServerLinksConfig {

    @Description({
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
