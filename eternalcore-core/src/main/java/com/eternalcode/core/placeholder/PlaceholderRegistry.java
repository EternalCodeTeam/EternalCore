package com.eternalcode.core.placeholder;

import com.eternalcode.core.placeholder.watcher.PlaceholderWatcher;
import com.eternalcode.core.placeholder.watcher.PlaceholderWatcherKey;
import com.eternalcode.core.viewer.Viewer;

import java.util.Optional;

public interface PlaceholderRegistry {

    void register(Placeholder placeholder);

    <T> PlaceholderWatcher<T> createWatcher(PlaceholderWatcherKey<T> name);

    String format(String text, Viewer target);

    Optional<NamedPlaceholder> getNamedPlaceholder(String name);

}
