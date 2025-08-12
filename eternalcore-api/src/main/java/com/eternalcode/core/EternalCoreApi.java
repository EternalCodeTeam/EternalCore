package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.jail.JailService;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.feature.msg.MsgService;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.warp.WarpService;

public interface EternalCoreApi {

    AfkService getAfkService();

    CatboyService getCatboyService();

    IgnoreService getIgnoreService();

    HomeService getHomeService();

    JailService getJailService();

    LanguageService getLanguageService();

    MsgService getMsgService();

    RandomTeleportService getRandomTeleportService();

    SpawnService getSpawnService();

    //TeleportService getTeleportService();

    WarpService getWarpService();
}
