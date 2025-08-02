package com.eternalcode.core.feature.teleportrequest;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;

public class TeleportRequestConfig extends OkaeriConfig implements TeleportRequestSettings {
    @Comment("# Time of tpa requests expire")
    public Duration tpaRequestExpire = Duration.ofSeconds(80);

    @Comment("")
    @Comment("# Time of teleportation time in /tpa commands")
    public Duration tpaTimer = Duration.ofSeconds(10);

    @Override
    public Duration teleportExpire() {
        return this.tpaRequestExpire;
    }

    @Override
    public Duration teleportTime() {
        return this.tpaTimer;
    }
}
