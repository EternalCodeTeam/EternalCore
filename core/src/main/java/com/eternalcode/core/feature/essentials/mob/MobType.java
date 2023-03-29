package com.eternalcode.core.feature.essentials.mob;

import lombok.Getter;
import lombok.Setter;
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

    @Getter @Setter
    private Class<? extends Entity> mobClass;

    @Getter
    private final boolean parseable;

    @Getter
    private final boolean suggeestable;

    MobType(@Nullable Class<? extends Entity> mobClass, boolean parseable, boolean suggeestable) {
        this.mobClass = mobClass;
        this.parseable = parseable;
        this.suggeestable = suggeestable;
    }

    MobType(boolean parseable, boolean suggeestable) {
        this.parseable = parseable;
        this.suggeestable = suggeestable;
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
