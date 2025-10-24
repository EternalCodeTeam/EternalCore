package com.eternalcode.core.feature.back;

import eu.okaeri.configs.OkaeriConfig;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class BackConfig extends OkaeriConfig implements BackSettings {

    public Duration backTeleportTimer = Duration.ofSeconds(5);
    public Duration backLocationCacheDuration = Duration.ofHours(1);
}
