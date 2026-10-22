package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.feature.jail.JailService;
import com.eternalcode.core.feature.msg.MsgService;
import com.eternalcode.core.feature.punishment.ban.BanService;
import com.eternalcode.core.feature.punishment.banip.BanIpService;
import com.eternalcode.core.feature.punishment.kick.KickService;
import com.eternalcode.core.feature.punishment.mute.MuteService;
import com.eternalcode.core.feature.punishment.notification.PunishmentNotificationService;
import com.eternalcode.core.feature.punishment.warn.WarnService;
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
    public CatboyService getCatboyService() {
        return this.dependencyProvider.getDependency(CatboyService.class);
    }

    @Override
    public IgnoreService getIgnoreService() {
        return this.dependencyProvider.getDependency(IgnoreService.class);
    }

    @Override
    public HomeService getHomeService() {
        return this.dependencyProvider.getDependency(HomeService.class);
    }

    @Override
    public JailService getJailService() {
        return this.dependencyProvider.getDependency(JailService.class);
    }

    @Override
    public MsgService getMsgService() {
        return this.dependencyProvider.getDependency(MsgService.class);
    }

    @Override
    public RandomTeleportService getRandomTeleportService() {
        return this.dependencyProvider.getDependency(RandomTeleportService.class);
    }

    @Override
    public SpawnService getSpawnService() {
        return this.dependencyProvider.getDependency(SpawnService.class);
    }

    @Override
    public TeleportService getTeleportService() {
        return this.dependencyProvider.getDependency(TeleportService.class);
    }

    @Override
    public WarpService getWarpService() {
        return this.dependencyProvider.getDependency(WarpService.class);
    }

    @Override
    public BanService getBanService() {
        return this.dependencyProvider.getDependency(BanService.class);
    }

    @Override
    public BanIpService getBanIpService() {
        return this.dependencyProvider.getDependency(BanIpService.class);
    }

    @Override
    public KickService getKickService() {
        return this.dependencyProvider.getDependency(KickService.class);
    }

    @Override
    public MuteService getMuteService() {
        return this.dependencyProvider.getDependency(MuteService.class);
    }

    @Override
    public WarnService getWarnService() {
        return this.dependencyProvider.getDependency(WarnService.class);
    }

    @Override
    public PunishmentNotificationService getPunishmentNotificationService() {
        return this.dependencyProvider.getDependency(PunishmentNotificationService.class);
    }
}
