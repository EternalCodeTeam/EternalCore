package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.privatechat.PrivateChatService;
import com.eternalcode.core.feature.randomteleport.RandomTeleportService;
import com.eternalcode.core.feature.spawn.SpawnService;
import com.eternalcode.core.feature.teleport.TeleportService;

public interface EternalCoreApi {

    AfkService getAfkService();

    SpawnService getSpawnService();

    CatboyService getCatboyService();

    TeleportService getTeleportService();

    RandomTeleportService getRandomTeleportService();

    PrivateChatService getPrivateChatService();

}
