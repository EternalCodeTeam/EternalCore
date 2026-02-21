package com.eternalcode.core.placeholder.watcher;

public record PlaceholderWatcherKey<T>(String name, Class<T> type) {

    public static <T> PlaceholderWatcherKey<T> of(String name, Class<T> type) {
        return new PlaceholderWatcherKey<>(name, type);
    }

}
