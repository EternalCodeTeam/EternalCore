package com.eternalcode.core.feature.heal;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Contextual
public class HealConfiguration {

    @Description({
        "If true, only negative potion effects will be removed on heal",
        "If false, all potion effects will be removed on heal"
    })
    public boolean removeOnlyNegativeEffects = true;
}
