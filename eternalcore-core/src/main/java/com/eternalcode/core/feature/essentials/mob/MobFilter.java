package com.eternalcode.core.feature.essentials.mob;

import org.bukkit.entity.Entity;

@FunctionalInterface
interface MobFilter {

    boolean filterMob(Entity entity);

}
