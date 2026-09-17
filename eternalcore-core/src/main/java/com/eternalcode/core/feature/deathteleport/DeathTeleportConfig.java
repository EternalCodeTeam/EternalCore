package com.eternalcode.core.feature.deathteleport;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DeathTeleportConfig extends OkaeriConfig implements DeathTeleportSettings {

    @Comment({
        "# Automatically teleport players back to their last death location after they respawn.",
        "# This helps new players who don't know the /back command to quickly return to where they died.",
        "# Players can opt out for themselves with the /deathteleport command."
    })
    public boolean deathTeleportEnabled = true;

    @Comment("# Delay after respawn before the player is teleported back to their death location.")
    public Duration deathTeleportDelay = Duration.ofSeconds(3);

    @Comment("# How long a death location is remembered and eligible for automatic teleportation.")
    public Duration deathLocationCacheDuration = Duration.ofHours(1);
}
