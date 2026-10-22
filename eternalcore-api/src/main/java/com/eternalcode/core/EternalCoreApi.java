package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.jail.JailService;
import com.eternalcode.core.feature.msg.MsgService;
import com.eternalcode.core.feature.punishment.ban.BanService;
import com.eternalcode.core.feature.punishment.banip.BanIpService;
import com.eternalcode.core.feature.punishment.kick.KickService;
import com.eternalcode.core.feature.punishment.mute.MuteService;
import com.eternalcode.core.feature.punishment.notification.PunishmentNotificationService;
import com.eternalcode.core.feature.punishment.warn.WarnService;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.warp.WarpService;

public interface EternalCoreApi {

    AfkService getAfkService();

    CatboyService getCatboyService();

    IgnoreService getIgnoreService();

    HomeService getHomeService();

    JailService getJailService();

    MsgService getMsgService();

    RandomTeleportService getRandomTeleportService();

    SpawnService getSpawnService();

    TeleportService getTeleportService();

    WarpService getWarpService();

    BanService getBanService();

    BanIpService getBanIpService();

    KickService getKickService();

    MuteService getMuteService();

    WarnService getWarnService();

    PunishmentNotificationService getPunishmentNotificationService();
}
