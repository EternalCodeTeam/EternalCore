package com.eternalcode.core.feature.automessage;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class AutoMessageConfig extends OkaeriConfig implements AutoMessageSettings {
    @Comment("# AutoMessage should be enabled?")
    public boolean enabled = true;

    @Comment("# Interval between messages")
    public Duration interval = Duration.ofSeconds(60);

    @Comment("# Draw mode (RANDOM, SEQUENTIAL)")
    public DrawMode drawMode = DrawMode.RANDOM;

    @Comment("# Minimum number of players on the server to send an auto message.")
    public int minPlayers = 1;
}
