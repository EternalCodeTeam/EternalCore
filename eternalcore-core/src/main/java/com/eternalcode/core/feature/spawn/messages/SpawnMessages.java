package com.eternalcode.core.feature.spawn.messages;

import com.eternalcode.core.feature.teleport.config.TeleportMessages;
import com.eternalcode.multification.notice.Notice;

public interface SpawnMessages extends TeleportMessages {

    Notice spawnSet();
    Notice spawnNoSet();

    Notice spawnTeleportedBy();
    Notice spawnTeleportedOther();

}
