package com.eternalcode.core.updater;

import com.eternalcode.commons.updater.UpdateResult;
import com.eternalcode.commons.updater.Version;
import com.eternalcode.commons.updater.impl.ModrinthUpdateChecker;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.concurrent.CompletableFuture;

@Service
class UpdaterService {

    private static final String MODRINTH_PROJECT_ID = "EternalCore";
    private static final String CACHE_KEY = "modrinth-update";

    private final AsyncLoadingCache<String, UpdateResult> updateCache;

    @Inject
    UpdaterService(PluginDescriptionFile pluginDescriptionFile) {
        Version currentVersion = new Version(pluginDescriptionFile.getVersion());
        ModrinthUpdateChecker updateChecker = new ModrinthUpdateChecker();

        this.updateCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofHours(1))
            .buildAsync(key -> updateChecker.check(MODRINTH_PROJECT_ID, currentVersion));
    }

    CompletableFuture<UpdateResult> checkForUpdate() {
        return this.updateCache.get(CACHE_KEY);
    }
}
