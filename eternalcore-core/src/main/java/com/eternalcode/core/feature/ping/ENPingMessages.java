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
    public Notice playerPing = Notice.chat("<green>► <white>Your ping is: <green>{PING}<white>ms");

    @Comment("# {PLAYER} - Target player, {PING} - Ping of target player")
    public Notice targetPlayerPing = Notice.chat("<green>► <white>Ping of the <green>{PLAYER} <white>is: <green>{PING}<white>ms");
}
