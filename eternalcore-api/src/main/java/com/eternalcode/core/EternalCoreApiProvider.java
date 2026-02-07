package com.eternalcode.core;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class EternalCoreApiProvider {

    @Nullable
    @ApiStatus.Internal
    private static EternalCoreApi api;

    @ApiStatus.Internal
    private EternalCoreApiProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    @NotNull
    public static EternalCoreApi provide() {
        if (api == null) {
            throw new IllegalStateException("EternalCoreApiProvider is not initialized");
        }

        return api;
    }

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
