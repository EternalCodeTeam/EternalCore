package com.eternalcode.core.feature.freeze.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFreezeMessages extends OkaeriConfig implements FreezeMessages {

    public Notice frozenSelf = Notice.chat("<green>► <white>You have frozen yourself for <green>{DURATION}</green>!</white>");

    public Notice unfrozenSelf = Notice.chat("<green>► <white>You have unfrozen yourself!</white>");

    public Notice frozenOther = Notice.chat("<green>► <white>You have frozen player <green>{PLAYER}</green> for <green>{DURATION}</green>!</white>");

    public Notice unfrozenOther = Notice.chat("<green>► <white>You have unfrozen player <green>{PLAYER}</green>!</white>");

    public Notice frozenByOther = Notice.chat("<green>► <white>You have been frozen by player <green>{PLAYER}</green> for <green>{DURATION}</green>!</white>");

    public Notice unfrozenByOther = Notice.chat("<green>► <white>You have been unfrozen by player <green>{PLAYER}</green>!</white>");

    public Notice selfNotFrozen = Notice.chat("<red>► <dark_red>You are not frozen!</dark_red>");

    public Notice otherNotFrozen = Notice.chat("<red>► <dark_red>Player <red>{PLAYER}</red> is not frozen!</dark_red>");
}
