package com.eternalcode.core.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class IntegrationTestSpec {

    public <T> T await(CompletableFuture<T> future) {
        return future
            .orTimeout(5, TimeUnit.SECONDS)
            .join();
    }
}
