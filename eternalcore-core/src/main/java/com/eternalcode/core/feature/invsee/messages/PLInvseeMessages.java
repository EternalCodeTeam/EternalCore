package com.eternalcode.core.feature.invsee.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLInvseeMessages extends OkaeriConfig implements InvseeMessages {
    Notice cannotInspectSelf = Notice.chat("<red>✘ <dark_red>Nie możesz sprawdzić samego siebie!");

    @Comment("# {PLAYER} - Inspected player")
    Notice inspectedPlayerDied = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>umarł, więc nie możesz sprawdzić jego ekwipunku!");

    @Comment("# {PLAYER} - Sprawdzany gracz")
    String inventoryTitle = "<dark_gray>Ekwipunek gracza <color:#9d6eef>{PLAYER}";
}
