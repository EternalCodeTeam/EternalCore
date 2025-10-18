package com.eternalcode.core.feature.ping;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPingMessages extends OkaeriConfig implements PingMessages {
    
    @Comment({"# {PING} - Aktualna ilość pingu."})
    public Notice playerPing = Notice.chat("<green>► <white>Twój ping: <green>{PING}<white>ms");
    
    @Comment("# {PING} - Aktualna ilość pingu dla gracza.")
    public Notice targetPlayerPing = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>ma ping: <green>{PING}<white>ms");
}
