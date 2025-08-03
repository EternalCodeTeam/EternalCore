package com.eternalcode.core.feature.troll.burn;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class BurnConfig extends OkaeriConfig implements BurnSettings {

    @Comment({" ", "# Default burn duration in ticks, if it's not specified in the command.",
            "# 100 ticks = 5 seconds"})
    public int defaultBurnDuration = 100;

    @Override
    public int defaultBurnDuration() {
        return this.defaultBurnDuration;
    }
}
