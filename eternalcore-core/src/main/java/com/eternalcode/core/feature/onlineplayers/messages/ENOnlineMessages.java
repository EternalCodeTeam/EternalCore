package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class ENOnlineMessages extends OkaeriConfig implements OnlineMessages {
    
    @Comment({" ", "# {ONLINE} - Number of online players"})
    public Notice onlinePlayersCountMessage = Notice.chat("<green>► <white>On server now is: <green>{ONLINE} <white>players!");

    @Comment("# {ONLINE} - Current online players, {PLAYERS} - Player list")
    public Notice onlinePlayersMessage = Notice.chat("<green>► <white>On server is: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <green>{PLAYERS}");

    public List<String> fullServerSlots = List.of(
        " ",
        "<red>✘ <dark_red>Server is full!",
        "<red>✘ <dark_red>Buy rank on our site!"
    );
}
