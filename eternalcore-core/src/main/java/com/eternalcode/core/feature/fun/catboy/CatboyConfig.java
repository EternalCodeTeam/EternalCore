package com.eternalcode.core.feature.fun.catboy;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class CatboyConfig extends OkaeriConfig implements CatboySettings {
    @Comment({
        "# Speed of player walk speed while using /catboy feature",
        "# Default minecraft walk speed is 0.2"
    })
    public float catboyWalkSpeed = 0.4F;
}
