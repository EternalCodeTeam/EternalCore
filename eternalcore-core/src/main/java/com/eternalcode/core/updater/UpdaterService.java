package com.eternalcode.core.updater;

import com.eternalcode.commons.updater.UpdateResult;
import com.eternalcode.commons.updater.Version;
import com.eternalcode.commons.updater.impl.ModrinthUpdateChecker;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.concurrent.CompletableFuture;

@Service
class UpdaterService {

    private static final String MODRINTH_PROJECT_ID = "EternalCore";

    private final ModrinthUpdateChecker updateChecker;
    private final Version currentVersion;

    @Inject
    UpdaterService(PluginDescriptionFile pluginDescriptionFile) {
        this.updateChecker = new ModrinthUpdateChecker();
        this.currentVersion = new Version(pluginDescriptionFile.getVersion());
    }

    CompletableFuture<UpdateResult> checkForUpdate() {
        return CompletableFuture.supplyAsync(() ->
            updateChecker.check(MODRINTH_PROJECT_ID, currentVersion)
        );
    }
}
