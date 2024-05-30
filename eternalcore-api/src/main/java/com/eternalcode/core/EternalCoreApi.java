package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.jail.JailService;
import com.eternalcode.core.feature.language.LanguageService;
import com.eternalcode.core.feature.privatechat.PrivateChatService;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.feature.warp.WarpService;

public interface EternalCoreApi {

    AfkService getAfkService();

    SpawnService getSpawnService();

    CatboyService getCatboyService();

    TeleportService getTeleportService();

    RandomTeleportService getRandomTeleportService();

    PrivateChatService getPrivateChatService();

    WarpService getWarpService();

    HomeService getHomeService();

    JailService getJailService();

    LanguageService getLanguageService();
}
