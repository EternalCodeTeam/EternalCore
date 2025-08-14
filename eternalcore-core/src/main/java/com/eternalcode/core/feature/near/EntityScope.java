package com.eternalcode.core.feature.near;

import lombok.Getter;
import org.bukkit.entity.*;

import java.util.Arrays;
import java.util.List;

public enum EntityScope {

    PLAYER("player", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> entity.getType().equals(EntityType.PLAYER))
                .toList();
        }
    }),
    HOSTILE("hostile", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> entity instanceof Monster)
                .toList();
        }
    }),
    PASSIVE("passive", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> entity instanceof Animals)
                .toList();
        }
    }),
    RAIDER("raider", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> entity instanceof Raider)
                .toList();
        }
    }),
    LIVING("mob", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> entity.getType().isAlive())
                .toList();
        }
    }),
    LEASHED("leashed", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities.stream()
                .filter(entity -> (entity instanceof LivingEntity && ((LivingEntity) entity).isLeashed()))
                .toList();
        }
    }),
    ALL("all", new EntityFilter() {
        @Override
        public List<Entity> filter(List<Entity> entities) {
            return entities;
        }
    });

    @Getter
    private final String name;
    private final EntityFilter filter;

    EntityScope(String name, EntityFilter filter) {
        this.name = name;
        this.filter = filter;
    }

    public List<Entity> filterEntities(List<Entity> entities) {
        return filter.filter(entities);
    }

    /**
     * Returns the EntityScope corresponding to the given name.
     *
     * @param name the name of the EntityScope
     * @return the EntityScope corresponding to the name
     * @throws IllegalArgumentException if no EntityScope with the given name exists
     */
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

    private interface EntityFilter {
        List<Entity> filter(List<Entity> entities);
    }

}
