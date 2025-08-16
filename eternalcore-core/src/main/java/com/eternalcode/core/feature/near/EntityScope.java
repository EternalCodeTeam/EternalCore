package com.eternalcode.core.feature.near;

import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Raider;

public enum EntityScope {

    PLAYER(
        "player", entities -> entities.stream()
        .filter(entity -> entity.getType().equals(EntityType.PLAYER))
        .toList()),
    HOSTILE(
        "hostile", entities -> entities.stream()
        .filter(entity -> entity instanceof Monster)
        .toList()),
    PASSIVE(
        "passive", entities -> entities.stream()
        .filter(entity -> entity instanceof Animals)
        .toList()),
    RAIDER(
        "raider", entities -> entities.stream()
        .filter(entity -> entity instanceof Raider)
        .toList()),
    LIVING(
        "mob", entities -> entities.stream()
        .filter(entity -> entity instanceof LivingEntity)
        .toList()),
    LEASHED(
        "leashed", entities -> entities.stream()
        .filter(entity -> (entity instanceof LivingEntity && ((LivingEntity) entity).isLeashed()))
        .toList()),
    ALL("all", entities -> entities);

    private final String name;
    private final EntityFilter filter;

    EntityScope(String name, EntityFilter filter) {
        this.name = name;
        this.filter = filter;
    }

    public static EntityScope fromName(String name) {
        for (EntityScope scope : values()) {
            if (scope.name.equalsIgnoreCase(name)) {
                return scope;
            }
        }
        throw new IllegalArgumentException("No EntityScope found for name: " + name);
    }

    public static List<String> getNames() {
        return Arrays.stream(EntityScope.values())
            .map(entityScope -> entityScope.getName().toLowerCase())
            .toList();
    }

    public List<Entity> filterEntities(List<Entity> entities) {
        return this.filter.filter(entities);
    }

    public String getName() {
        return this.name;
    }

    private interface EntityFilter {
        List<Entity> filter(List<Entity> entities);
    }
}
