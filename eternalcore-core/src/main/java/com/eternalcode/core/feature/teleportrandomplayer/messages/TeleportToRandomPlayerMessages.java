package com.eternalcode.core.feature.teleportrandomplayer.messages;

import com.eternalcode.multification.notice.Notice;

public interface TeleportToRandomPlayerMessages {
    // teleport to random player command
    Notice randomPlayerNotFound();
    Notice teleportedToRandomPlayer();
    Notice randomPlayerInRangeNotFound();
    Notice teleportedToRandomPlayerInRange();
    Notice teleportedToRandomPlayerInRangeNotFound();
}
