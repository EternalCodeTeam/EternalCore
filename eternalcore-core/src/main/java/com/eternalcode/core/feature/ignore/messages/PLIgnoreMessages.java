package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLIgnoreMessages extends OkaeriConfig implements IgnoreMessages {

    Notice playerIgnored = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");
    Notice allPlayersIgnored = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
    Notice cannotIgnoreSelf = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");
    Notice playerAlreadyIgnored = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>jest już zignorowany!");
    Notice playerUnignored = Notice.chat("<red>► <dark_red>Odignorowano gracza <red>{PLAYER}<dark_red>!");
    Notice allPlayersUnignored = Notice.chat("<red>► <dark_red>Odignorowano wszystkich graczy!");
    Notice cannotUnignoreSelf = Notice.chat("<red>► <dark_red>Nie możesz odignorować samego siebie!");
    Notice playerNotIgnored = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany!");
}
