package com.eternalcode.core.feature.freeze.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLFreezeMessages extends OkaeriConfig implements  FreezeMessages {

    Notice frozenSelf = Notice.chat("<color:#9d6eef>► <white>Zamroziłeś się na czas <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenSelf = Notice.chat("<color:#9d6eef>► <white>Odmroziłeś się!</white>");

    Notice frozenOther = Notice.chat("<color:#9d6eef>► <white>Zamroziłeś gracza <color:#9d6eef>{PLAYER}</color:#9d6eef> na czas <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenOther = Notice.chat("<color:#9d6eef>► <white>Odmroziłeś gracza <color:#9d6eef>{PLAYER}</color:#9d6eef>!</white>");

    Notice frozenByOther = Notice.chat("<color:#9d6eef>► <white>Zostałeś zamrożony przez gracza <color:#9d6eef>{PLAYER}</color:#9d6eef> na czas <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenByOther = Notice.chat("<color:#9d6eef>► <white>Zostałeś odmrożony przez gracza <color:#9d6eef>{PLAYER}</color:#9d6eef>!</white>");

    Notice selfNotFrozen = Notice.chat("<red>► <dark_red>Nie jesteś zamrożony!</dark_red>");

    Notice otherNotFrozen = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER}</red> nie jest zamrożony!</dark_red>");
}
