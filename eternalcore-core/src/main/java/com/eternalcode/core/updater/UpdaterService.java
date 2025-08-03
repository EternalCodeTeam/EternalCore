package com.eternalcode.core.updater;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.gitcheck.GitCheck;
import com.eternalcode.gitcheck.GitCheckResult;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import panda.std.Lazy;

@Service
class UpdaterService {

    private static final GitRepository GIT_REPOSITORY = GitRepository.of("EternalCodeTeam", "EternalCore");

    private final GitCheck gitCheck = new GitCheck();
    private final Lazy<GitCheckResult> gitCheckResult;

    @Inject
    UpdaterService(PluginDescriptionFile pluginDescriptionFile) {
        this.gitCheckResult = new Lazy<>(() -> {
            try {
                String version = pluginDescriptionFile.getVersion();
                return this.gitCheck.checkRelease(GIT_REPOSITORY, GitTag.of("v" + version));
            }
            catch (Exception exception) {
                return null;
            }
        });
    }

    CompletableFuture<Boolean> isUpToDate() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GitCheckResult checkResult = this.gitCheckResult.get();
                return checkResult == null || checkResult.isUpToDate();
            }
            catch (Exception exception) {
                return true;
            }
        });
    }
}
