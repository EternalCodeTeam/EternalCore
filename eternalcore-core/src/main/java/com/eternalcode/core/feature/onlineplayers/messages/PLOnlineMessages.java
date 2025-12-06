package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLOnlineMessages extends OkaeriConfig implements OnlineMessages {

    Notice onlinePlayersCount = Notice
            .chat("<color:#9d6eef>► <white>Na serwerze jest: <color:#9d6eef>{ONLINE} <white>graczy online!");
    Notice onlinePlayersList = Notice.chat(
            "<color:#9d6eef>► <white>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <color:#9d6eef>{PLAYERS} ");

    public List<String> serverFull = List.of(
            " ",
            "<red>✘ <dark_red>Serwer jest pełen!",
            "<red>✘ <dark_red>Zakup rangę na naszej stronie!");
}
