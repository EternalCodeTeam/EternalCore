package com.eternalcode.core.feature.ping;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPingMessages extends OkaeriConfig implements PingMessages {
    Notice playerPing = Notice.chat("<color:#9d6eef>► <white>Twój ping: <color:#9d6eef>{PING}ms");
    Notice targetPlayerPing = Notice.chat("<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>ma ping: <color:#9d6eef>{PING}ms");
}
