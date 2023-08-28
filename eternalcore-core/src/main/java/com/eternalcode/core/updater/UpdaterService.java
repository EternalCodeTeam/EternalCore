package com.eternalcode.core.updater;

import com.eternalcode.gitcheck.GitCheck;
import com.eternalcode.gitcheck.GitCheckResult;
import com.eternalcode.gitcheck.git.GitRepository;
import com.eternalcode.gitcheck.git.GitTag;
import org.bukkit.plugin.PluginDescriptionFile;
import panda.std.Lazy;

import java.util.concurrent.CompletableFuture;

public class UpdaterService {

    private static final GitRepository GIT_REPOSITORY = GitRepository.of("EternalCodeTeam", "EternalCore");

    private final GitCheck gitCheck = new GitCheck();
    private final Lazy<GitCheckResult> gitCheckResult;

    public UpdaterService(PluginDescriptionFile pluginDescriptionFile) {
        this.gitCheckResult = new Lazy<>(() -> {
            String version = pluginDescriptionFile.getVersion();

            return this.gitCheck.checkRelease(GIT_REPOSITORY, GitTag.of("v" + version));
        });
    }

    public CompletableFuture<Boolean> isUpToDate() {
        return CompletableFuture.supplyAsync(() -> {
            GitCheckResult checkResult = this.gitCheckResult.get();

            return checkResult.isUpToDate();
        });
    }
}
