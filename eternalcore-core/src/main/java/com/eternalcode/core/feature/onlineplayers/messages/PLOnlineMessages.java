package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLOnlineMessages extends OkaeriConfig implements OnlineMessages {

    @Comment({"# {ONLINE} - Aktualna ilość graczy online"})
    public Notice onlinePlayersCount = Notice.chat("<green>► <white>Na serwerze jest: <green>{ONLINE} <white>graczy online!");

    @Comment("# {ONLINE} - Aktualna ilość graczy online, {PLAYERS} - Lista graczy online")
    public Notice onlinePlayersList = Notice.chat("<green>► <white>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS} ");

    public List<String> serverFull = List.of(
        " ",
        "<green>► <white>Serwer jest pełen!",
        "<green>► <white>Zakup rangę na naszej stronie!"
    );
}
