package com.eternalcode.core.placeholder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class AsyncPlaceholderValue<T> {
    private final CompletableFuture<T> future;
    private final Function<T, String> formatter;

    public AsyncPlaceholderValue(CompletableFuture<T> future, Function<T, String> formatter) {
        this.future = future;
        this.formatter = formatter;
    }

    @Override
    public String toString() {
        if (!future.isDone()) {
            return "Loading...";
        }

        try {
            T value = future.getNow(null);
            return value != null ? formatter.apply(value) : "Unknown";
        }
        catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
