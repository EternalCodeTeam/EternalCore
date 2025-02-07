package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;

public interface SpawnMessages {
    // spawn
    Notice spawnSet();
    Notice spawnNoSet();

    Notice spawnTeleportedBy();
    Notice spawnTeleportedOther();
}
