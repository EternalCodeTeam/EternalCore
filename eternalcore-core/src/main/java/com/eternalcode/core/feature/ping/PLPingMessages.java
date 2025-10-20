package com.eternalcode.core.feature.ping;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPingMessages extends OkaeriConfig implements PingMessages {
    Notice playerPing = Notice.chat("<green>► <white>Twój ping: <green>{PING}<white>ms");
    Notice targetPlayerPing = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>ma ping: <green>{PING}<white>ms");
}
