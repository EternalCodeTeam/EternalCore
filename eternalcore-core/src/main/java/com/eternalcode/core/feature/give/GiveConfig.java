package com.eternalcode.core.feature.give;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class GiveConfig extends OkaeriConfig implements GiveSettings {
    @Comment("# Default amount of items to give when no amount is specified")
    public int defaultGiveAmount = 1;

    @Comment("# Drop items on ground when player's inventory is full")
    public boolean dropOnFullInventory = true;
}
