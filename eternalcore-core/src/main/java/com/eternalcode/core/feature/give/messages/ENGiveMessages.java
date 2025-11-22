package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGiveMessages extends OkaeriConfig implements GiveMessages {
    Notice itemGivenByAdmin = Notice.chat("<color:#9d6eef>► <white>You have received: <color:#9d6eef>{ITEM}");
    Notice itemGivenToTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Player <color:#9d6eef>{PLAYER} <white>has received <color:#9d6eef>{ITEM}");
    Notice noSpace = Notice.chat("<red>✘ <dark_red>Not enough space in inventory!");
    Notice itemNotFound = Notice.chat("<color:#9d6eef>► <white>Not a valid obtainable item!");
}
