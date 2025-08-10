package com.eternalcode.core.feature.teleportrandomplayer;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TeleportToRandomPlayerConfig extends OkaeriConfig implements TeleportToRandomPlayerSettings {

    @Comment("# Should random teleport pick OP players too?")
    public boolean teleportToOpPlayers = false;

}
