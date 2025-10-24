package com.eternalcode.core.feature.teleportrequest;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TeleportRequestConfig extends OkaeriConfig implements TeleportRequestSettings {
    @Comment("# Time of tpa requests expire")
    public Duration tpaRequestExpire = Duration.ofSeconds(80);

    @Comment("# Time of teleportation time in /tpa commands.")
    public Duration tpaTimer = Duration.ofSeconds(10);
}
