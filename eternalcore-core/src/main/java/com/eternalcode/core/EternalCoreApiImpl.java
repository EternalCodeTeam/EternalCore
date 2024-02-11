package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.DependencyProvider;

class EternalCoreApiImpl implements EternalCoreApi {

    private final DependencyProvider dependencyProvider;

    public EternalCoreApiImpl(DependencyProvider dependencyProvider) {
        this.dependencyProvider = dependencyProvider;
    }

    @Override
    public AfkService getAfkService() {
        return this.dependencyProvider.getDependency(AfkService.class);
    }

    @Override
    public SpawnService getSpawnService() {
        return this.dependencyProvider.getDependency(SpawnService.class);
    }

    @Override
    public CatboyService getCatboyService() {
        return this.dependencyProvider.getDependency(CatboyService.class);
    }

    @Override
    public TeleportService getTeleportService() {
        return this.dependencyProvider.getDependency(TeleportService.class);
    }

    @Override
    public RandomTeleportService getRandomTeleportService() {
        return this.dependencyProvider.getDependency(RandomTeleportService.class);
    }

    // WARP SERVICE HERE! ⚠⚠⚠⚠⚠

}
