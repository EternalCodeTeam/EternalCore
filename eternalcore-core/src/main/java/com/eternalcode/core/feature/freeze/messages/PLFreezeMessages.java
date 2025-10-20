package com.eternalcode.core.feature.freeze.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFreezeMessages extends OkaeriConfig implements  FreezeMessages {

    Notice frozenSelf = Notice.chat("<green>► <white>Zamroziłeś się na czas <green>{DURATION}</green>!</white>");

    Notice unfrozenSelf = Notice.chat("<green>► <white>Odmroziłeś się!</white>");

    Notice frozenOther = Notice.chat("<green>► <white>Zamroziłeś gracza <green>{PLAYER}</green> na czas <green>{DURATION}</green>!</white>");

    Notice unfrozenOther = Notice.chat("<green>► <white>Odmroziłeś gracza <green>{PLAYER}</green>!</white>");

    Notice frozenByOther = Notice.chat("<green>► <white>Zostałeś zamrożony przez gracza <green>{PLAYER}</green> na czas <green>{DURATION}</green>!</white>");

    Notice unfrozenByOther = Notice.chat("<green>► <white>Zostałeś odmrożony przez gracza <green>{PLAYER}</green>!</white>");

    Notice selfNotFrozen = Notice.chat("<red>► <dark_red>Nie jesteś zamrożony!</dark_red>");

    Notice otherNotFrozen = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER}</red> nie jest zamrożony!</dark_red>");
}
