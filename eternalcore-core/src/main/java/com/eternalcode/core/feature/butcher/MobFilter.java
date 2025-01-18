package com.eternalcode.core.feature.butcher;

import org.bukkit.entity.Entity;

@FunctionalInterface
interface MobFilter {

    boolean filterMob(Entity entity);

}
