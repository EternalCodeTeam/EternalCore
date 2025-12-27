package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLIgnoreMessages extends OkaeriConfig implements IgnoreMessages {

    Notice playerIgnored = Notice.chat("<color:#9d6eef>► <white>Zignorowano gracza <red>{PLAYER}<white>!");
    Notice allPlayersIgnored = Notice.chat("<color:#9d6eef>► <white>Zignorowano wszystkich graczy!");
    Notice cannotIgnoreSelf = Notice.chat("<red>✘ <dark_red>Nie możesz zignorować samego siebie!");
    Notice playerAlreadyIgnored = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>jest już zignorowany!");
    Notice playerUnignored = Notice.chat("<color:#9d6eef>► <white>Odignorowano gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice allPlayersUnignored = Notice.chat("<color:#9d6eef>► <white>Odignorowano wszystkich graczy!");
    Notice cannotUnignoreSelf = Notice.chat("<red>✘ <dark_red>Nie możesz odignorować samego siebie!");
    Notice playerNotIgnored = Notice
            .chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany!");
}
