package com.eternalcode.core.placeholder;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.watcher.PlaceholderWatcher;
import com.eternalcode.core.placeholder.watcher.PlaceholderWatcherKey;
import com.eternalcode.core.viewer.Viewer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PlaceholderBukkitRegistryImpl implements PlaceholderRegistry {

    private final Server server;
    private final Set<Placeholder> placeholders = new HashSet<>();
    private final Map<String, NamedPlaceholder> namedPlaceholders = new HashMap<>();
    private final Map<PlaceholderWatcherKey<?>, Set<PlaceholderAsync<?>>> asyncPlaceholders = new HashMap<>();

    @Inject
    public PlaceholderBukkitRegistryImpl(Server server) {
        this.server = server;
    }

    @Override
    public void register(Placeholder placeholder) {
        this.placeholders.add(placeholder);

        if (placeholder instanceof NamedPlaceholder namedPlaceholder) {
            this.namedPlaceholders.put(namedPlaceholder.getName(), namedPlaceholder);
        }

        if (placeholder instanceof PlaceholderAsync<?> placeholderAsync) {
            PlaceholderWatcherKey<?> key = placeholderAsync.key();
            this.asyncPlaceholders.computeIfAbsent(key, k -> new HashSet<>()).add(placeholderAsync);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> PlaceholderWatcher<T> createWatcher(PlaceholderWatcherKey<T> key) {
        return (player, value) -> {
            PlaceholderAsync<T> async = (PlaceholderAsync<T>) asyncPlaceholders.get(key);
            if (async != null) {
                async.update(player, value);
            }
            return value;
        };
    }

    @Override
    public String format(String text, Viewer target) {
        if (!target.isConsole()) {
            Player playerTarget = this.server.getPlayer(target.getUniqueId());

            if (playerTarget != null) {
                for (Placeholder replacer : this.placeholders) {
                    text = replacer.apply(text, playerTarget);
                }
            }
        }

        return text;
    }

    @Override
    public Optional<NamedPlaceholder> getNamedPlaceholder(String name) {
        return Optional.ofNullable(this.namedPlaceholders.get(name));
    }

}
