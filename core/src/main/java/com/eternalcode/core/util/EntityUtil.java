package com.eternalcode.core.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

public class EntityUtil {

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
