package com.eternalcode.core.feature.butcher;

import com.eternalcode.core.util.EntityUtil;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.ArmorStand;

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
            case VEHICLE -> EntityUtil.is(entity, Vehicle.class);
            case ARMOR_STAND -> EntityUtil.is(entity, ArmorStand.class);
            case OTHER -> EntityUtil.is(entity, this.entityClass);
            case ALL -> EntityUtil.isMob(entity);
        };
    }
}
