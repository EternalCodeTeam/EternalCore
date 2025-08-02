package com.eternalcode.core.feature.repair;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class RepairConfig extends OkaeriConfig {

    @Comment("# Repair command cooldown")
    public Duration repairDelay = Duration.ofSeconds(5);
}
