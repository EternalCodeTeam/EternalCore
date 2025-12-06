package com.eternalcode.core.feature.ping;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENPingMessages extends OkaeriConfig implements PingMessages {
    
    @Comment({" ", "# {PING} - Current ping"})
    Notice playerPing = Notice.chat("<color:#9d6eef>► <white>Your ping is: <color:#9d6eef>{PING}ms");

    @Comment("# {PLAYER} - Target player, {PING} - Ping of target player")
    Notice targetPlayerPing = Notice.chat("<color:#9d6eef>► <white>Ping of player <color:#9d6eef>{PLAYER} <white>is: "
        + "<color:#9d6eef>{PING}ms");
}
