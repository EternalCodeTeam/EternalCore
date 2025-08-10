package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.multification.notice.Notice;

public interface SpawnMessages {

    Notice spawnSet();
    Notice spawnNoSet();

    Notice spawnTeleportedBy();
    Notice spawnTeleportedOther();

}
