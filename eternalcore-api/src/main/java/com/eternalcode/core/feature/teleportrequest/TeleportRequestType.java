package com.eternalcode.core.feature.teleportrequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus.Experimental;

// In next versions, this class can change their possible values, so it is recommended to use the constants provided by this class instead of creating new instances.
@Experimental
public record TeleportRequestType(String name) {

    private static final Map<String, TeleportRequestType> REGISTRY = new LinkedHashMap<>();

    public static final TeleportRequestType TELEPORT_TO_PLAYER = register("tpa");
    public static final TeleportRequestType TELEPORT_HERE_PLAYER = register("tpahere");

    private static TeleportRequestType register(String name) {
        TeleportRequestType type = new TeleportRequestType(name);

        if (REGISTRY.putIfAbsent(normalize(name), type) != null) {
            throw new IllegalStateException("Teleport request type '" + name + "' is already registered");
        }

        return type;
    }

    public static List<TeleportRequestType> values() {
        return List.copyOf(REGISTRY.values());
    }

    public static TeleportRequestType find(String name) {
        if (name == null) {
            return null;
        }

        TeleportRequestType type = REGISTRY.get(normalize(name));

        if (type == null) {
            throw new IllegalArgumentException("Teleport request type '" + name + "' is not registered");
        }

        return type;
    }

    public static TeleportRequestType valueOf(String name) {
        return find(name);
    }

    private static String normalize(String name) {
        return name.toLowerCase(Locale.ROOT);
    }
}
