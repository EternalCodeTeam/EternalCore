package com.eternalcode.core.feature.teleport.rtp.messages;

import com.eternalcode.multification.notice.Notice;

public interface TeleportToRandomPlayerMessages {
    Notice randomPlayerNotFound();
    Notice teleportedToRandomPlayer();
    Notice randomPlayerInRangeNotFound();
    Notice teleportedToRandomPlayerInRange();
}
