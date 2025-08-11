package com.eternalcode.core.feature.broadcast;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;

@Getter
@Accessors(fluent = true)
public class BroadcastConfig extends OkaeriConfig implements BroadcastSettings {

    @Comment("# Title settings")
    public Duration titleFadeIn = Duration.ofMillis(500);
    public Duration titleStay = Duration.ofMillis(2000);
    public Duration titleFadeOut = Duration.ofMillis(500);

}
