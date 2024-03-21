package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.jail.JailService;
import com.eternalcode.core.feature.privatechat.PrivateChatService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.warp.WarpService;
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

    @Override
    public PrivateChatService getPrivateChatService() {
        return this.dependencyProvider.getDependency(PrivateChatService.class);
    }

    @Override
    public WarpService getWarpService() {
        return this.dependencyProvider.getDependency(WarpService.class);
    }

    @Override
    public JailService getJailService() {
        return this.dependencyProvider.getDependency(JailService.class);
    }

    @Override
    public HomeService getHomeService() {
        return this.dependencyProvider.getDependency(HomeService.class);
    }

}
