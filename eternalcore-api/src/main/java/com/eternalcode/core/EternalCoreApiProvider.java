package com.eternalcode.core;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

public final class EternalCoreApiProvider {

    @Nullable
    private static EternalCoreApi api;

    private EternalCoreApiProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static EternalCoreApi provide() {
        if (api == null) {
            throw new IllegalStateException("EternalCoreApiProvider is not initialized");
        }

        return api;
    }

    @ApiStatus.Internal
    static void initialize(EternalCoreApi api) {
        if (EternalCoreApiProvider.api != null) {
            throw new IllegalStateException("EternalCoreApiProvider is already initialized");
        }

        if (api == null) {
            throw new IllegalArgumentException("api cannot be null");
        }

        EternalCoreApiProvider.api = api;
    }

    static void deinitialize() {
        if (EternalCoreApiProvider.api == null) {
            throw new IllegalStateException("EternalCoreApiProvider is not initialized");
        }

        EternalCoreApiProvider.api = null;
    }

}
