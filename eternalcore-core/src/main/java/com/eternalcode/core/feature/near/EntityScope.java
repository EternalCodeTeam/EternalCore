package com.eternalcode.core.feature.near;

import java.util.List;
import java.util.function.Predicate;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Raider;

record EntityScope(String name, Predicate<Entity> filter) {

    public static final EntityScope PLAYER = new EntityScope("player", entity -> entity.getType().equals(EntityType.PLAYER));
    public static final EntityScope HOSTILE = new EntityScope("hostile", entity -> entity instanceof Monster);
    public static final EntityScope PASSIVE = new EntityScope("passive", entity -> entity instanceof Animals);
    public static final EntityScope RAIDER = new EntityScope("raider", entity -> entity instanceof Raider);
    public static final EntityScope LIVING = new EntityScope("mob", entity -> entity instanceof Mob);
    public static final EntityScope LEASHED = new EntityScope("leashed", entity -> entity instanceof LivingEntity living && living.isLeashed());
    public static final EntityScope ALL = new EntityScope("all", entities -> true);

    private static final List<EntityScope> VALUES = List.of(PLAYER, HOSTILE, PASSIVE, RAIDER, LIVING, LEASHED, ALL);

    static EntityScope from(String name) {
        for (EntityScope scope : VALUES) {
            if (scope.name.equalsIgnoreCase(name)) {
                return scope;
            }
        }
        throw new IllegalArgumentException("No EntityScope found for name: " + name);
    }

    static List<EntityScope> values() {
        return VALUES;
    }

}
