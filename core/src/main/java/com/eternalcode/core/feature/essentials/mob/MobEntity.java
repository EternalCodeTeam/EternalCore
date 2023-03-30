package com.eternalcode.core.feature.essentials.mob;

import org.bukkit.entity.Entity;

public class MobEntity {

    private MobType mobType;
    private Class<? extends Entity> entityClass;

    public MobEntity(MobType mobType) {
        this.mobType = mobType;
    }

    public Class<? extends Entity> getEntityClass() {
        return this.entityClass;
    }

    public void setMobClass(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public MobType getMobType() {
        return this.mobType;
    }

    public void setMobType(MobType mobType) {
        this.mobType = mobType;
    }
}
