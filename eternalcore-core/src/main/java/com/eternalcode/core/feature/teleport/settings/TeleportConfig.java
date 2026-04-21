package com.eternalcode.core.feature.teleport.settings;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TeleportConfig extends OkaeriConfig implements TeleportSettings {

    @Comment({
        "# If true, the teleportation will be cancelled if the player moves during the teleportation process."
    })
    public boolean movementCancelsTeleport = true;
}
