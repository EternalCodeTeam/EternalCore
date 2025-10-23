package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGiveMessages extends OkaeriConfig implements GiveMessages {
    Notice itemGivenByAdmin = Notice.chat("<green>► <white>You have received: <green>{ITEM}");
    Notice itemGivenToTargetPlayer = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has received <green>{ITEM}");
    Notice noSpace = Notice.chat("<red>✘ <dark_red>Not enough space in inventory!");
    Notice itemNotFound = Notice.chat("<green>► <white>Not a valid obtainable item!");
}
