package com.eternalcode.core.feature.troll.burn;

public interface BurnSettings {
    /**
     * Gets the default burn duration in ticks, if it's not specified in the command.
     *
     * @return the default burn duration in ticks
     */
    int defaultBurnDuration();
}
