package com.eternalcode.core.feature.deathmessage.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class DeathMessageConfig extends OkaeriConfig implements DeathMessageSettings {

    @Comment("# Enable/disable custom death messages")
    @Comment("# false = use default Minecraft death messages")
    @Comment("# true = use custom messages from translations")
    public boolean customMessagesEnabled = true;

    @Comment("# Maximum time (in ticks) since last damage to consider it as the cause of death")
    @Comment("# 100 ticks = 5 seconds")
    public int maxDamageCauseAge = 100;
}
