package com.eternalcode.core.feature.back;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class BackConfig extends OkaeriConfig implements BackSettings {

    @Comment("# Time of teleportation time in /back command.")
    public Duration backTeleportTime = Duration.ofSeconds(5);

    @Comment("# Duration of caching last locations for /back command.")
    public Duration backLocationCacheDuration = Duration.ofHours(1);
}
