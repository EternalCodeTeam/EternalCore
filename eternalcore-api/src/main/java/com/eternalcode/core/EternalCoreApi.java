package com.eternalcode.core;

import com.eternalcode.core.feature.afk.AfkService;
import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.spawn.SpawnService;

public interface EternalCoreApi {

    AfkService getAfkService();

    SpawnService getSpawnService();

    CatboyService getCatboyService();

}
