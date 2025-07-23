package com.eternalcode.core.feature.heal;

import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;

@Header({
    " ",
    "# Settings for player healing behavior and potion effect removal"
})
public class HealConfiguration {

    @Comment({
        "Whether to remove only negative potion effects when healing.",
        "if true, remove only negative effects like poison or slowness",
        "if false, remove all effects including positive ones like strength or speed"
    })
    public boolean removeOnlyNegativeEffects = true;
}
