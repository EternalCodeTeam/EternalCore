package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.util.EntityUtil;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

class MobEntity {

    private final MobType mobType;
    private Class<? extends Entity> entityClass;

    MobEntity(MobType mobType) {
        this.mobType = mobType;
    }

    MobEntity(MobType mobType, Class<? extends Entity> entityClass) {
        this.mobType = mobType;
        this.entityClass = entityClass;
    }

    boolean isMatch(Entity entity) {
        return switch (this.mobType) {
            case PASSIVE -> EntityUtil.is(entity, Animals.class);
            case AGGRESSIVE -> EntityUtil.is(entity, Monster.class);
            case OTHER -> EntityUtil.is(entity, this.entityClass);
            case ALL -> EntityUtil.isMob(entity);
        };
    }
}
