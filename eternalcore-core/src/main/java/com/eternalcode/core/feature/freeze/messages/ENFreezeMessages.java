package com.eternalcode.core.feature.freeze.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFreezeMessages extends OkaeriConfig implements FreezeMessages {

    Notice frozenSelf = Notice.chat("<color:#9d6eef>► <white>You have frozen yourself for <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenSelf = Notice.chat("<color:#9d6eef>► <white>You have unfrozen yourself!</white>");

    Notice frozenOther = Notice.chat("<color:#9d6eef>► <white>You have frozen player <color:#9d6eef>{PLAYER}</color:#9d6eef> for <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenOther = Notice.chat("<color:#9d6eef>► <white>You have unfrozen player <color:#9d6eef>{PLAYER}</color:#9d6eef>!</white>");

    Notice frozenByOther = Notice.chat("<color:#9d6eef>► <white>You have been frozen by player <color:#9d6eef>{PLAYER}</color:#9d6eef> for <color:#9d6eef>{DURATION}</color:#9d6eef>!</white>");

    Notice unfrozenByOther = Notice.chat("<color:#9d6eef>► <white>You have been unfrozen by player <color:#9d6eef>{PLAYER}</color:#9d6eef>!</white>");

    Notice selfNotFrozen = Notice.chat("<red>► <dark_red>You are not frozen!</dark_red>");

    Notice otherNotFrozen = Notice.chat("<red>► <dark_red>Player <red>{PLAYER}</red> is not frozen!</dark_red>");
}
