package com.eternalcode.core;

public final class EternalCoreApiProvider {

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
