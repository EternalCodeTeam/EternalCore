package com.eternalcode.core.placeholder;

import com.eternalcode.core.placeholder.watcher.PlaceholderWatcherKey;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;

import java.util.function.Function;

public interface Placeholder {

    String apply(String text, Player targetPlayer);

    static Placeholder of(String target, String replacement) {
        return new StaticValuePlaceholder(target, player -> replacement);
    }

    static Placeholder of(String target, Function<Player, String> replacement) {
        return new StaticValuePlaceholder(target, replacement);
    }

    static Placeholder of(String target, Placeholder placeholder) {
        return new StaticValuePlaceholder(target, player -> placeholder.apply(target, player));
    }

    static <T> Placeholder async(
        String target,
        PlaceholderWatcherKey<T> key,
        Function<UUID, CompletableFuture<T>> loading,
        Function<T, String> mapper
    ) {
        return new PlaceholderAsync<>(target, key, loading, mapper);
    }

}
