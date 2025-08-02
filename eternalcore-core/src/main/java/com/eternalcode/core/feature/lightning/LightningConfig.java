package com.eternalcode.core.feature.lightning;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class LightningConfig extends OkaeriConfig implements LightningSettings {
    @Comment({" ", "# Maximum distance for the lightning strike block when using /lightning."})
    @Comment("# If you will look at a block that is further than this distance, the lightning will strike at the player.")
    public int maxLightningBlockDistance = 100;

    @Comment({" ", "# If there is no block in the range of lightning strike, should the lightning strike the player?"})
    public boolean lightningStrikePlayerIfNoBlock = true;
}
