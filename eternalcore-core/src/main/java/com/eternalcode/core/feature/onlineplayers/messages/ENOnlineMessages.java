package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENOnlineMessages extends OkaeriConfig implements OnlineMessages {

    public Notice onlinePlayersCount = Notice.chat("<green>► <white>Active players: <green>{ONLINE}<white>!");
    public Notice onlinePlayersList = Notice.chat("<green>► <white>Now active on this server: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS}");

    public List<String> serverFull = List.of(
        " ",
        "<red>✘ <dark_red>Server is full!",
        "<red>✘ <dark_red>Buy rank on our site!"
    );
}
