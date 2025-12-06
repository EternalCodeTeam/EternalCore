package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENOnlineMessages extends OkaeriConfig implements OnlineMessages {

    Notice onlinePlayersCount = Notice.chat("<color:#9d6eef>► <white>Active players: <color:#9d6eef>{ONLINE}<white>!");
    Notice onlinePlayersList = Notice.chat("<color:#9d6eef>► <white>Now active on this server: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <color:#9d6eef>{PLAYERS}");

    public List<String> serverFull = List.of(
        " ",
        "<red>✘ <dark_red>Server is full!",
        "<red>✘ <dark_red>Buy rank on our site!"
    );
}
