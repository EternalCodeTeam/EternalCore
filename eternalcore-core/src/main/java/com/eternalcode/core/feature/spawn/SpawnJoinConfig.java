package com.eternalcode.core.feature.spawn;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class SpawnJoinConfig extends OkaeriConfig implements SpawnJoinSettings {
    @Comment("# Teleport to spawn on first join")
    public boolean teleportToSpawnOnFirstJoin = true;

    @Comment("# Teleport to spawn on join")
    public boolean teleportToSpawnOnJoin = false;
}
