package com.eternalcode.core.feature.essentials.mob;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.Nullable;

public enum MobType {
    PASSIVE(Animals.class, true, true),
    AGGRESSIVE(Monster.class, true, true),
    OTHER(false, false),
    UNDEFINED(false, false),
    ALL(true, true);

    private Class<? extends Entity> mobClass;

    private final boolean isParseable;
    private final boolean isSuggeestable;

    MobType(@Nullable Class<? extends Entity> mobClass, boolean isParseable, boolean isSuggeestable) {
        this.mobClass = mobClass;
        this.isParseable = isParseable;
        this.isSuggeestable = isSuggeestable;
    }

    MobType(boolean parseable, boolean isSuggeestable) {
        this.isParseable = parseable;
        this.isSuggeestable = isSuggeestable;
    }

    public Class<? extends Entity> getMobClass() {
        return this.mobClass;
    }

    public void setMobClass(Class<? extends Entity> mobClass) {
        this.mobClass = mobClass;
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public boolean isSuggeestable() {
        return this.isSuggeestable;
    }

    public static boolean is(Entity entity, Class<? extends Entity> entityClass) {
        return is(entity.getClass(), entityClass);
    }

    public static boolean is(Class<?> clazz, Class<? extends Entity> entityClass) {
        return entityClass != null && clazz != null && entityClass.isAssignableFrom(clazz);
    }

    public static boolean isMob(Entity entity) {
        return isMob(entity.getClass());
    }

    public static boolean isMob(EntityType entityType) {
        Class<? extends Entity> entityClass = entityType.getEntityClass();
        return isMob(entityClass);
    }

    public static boolean isMob(Class<? extends Entity> entityClass) {
        return is(entityClass, Mob.class);
    }
}
