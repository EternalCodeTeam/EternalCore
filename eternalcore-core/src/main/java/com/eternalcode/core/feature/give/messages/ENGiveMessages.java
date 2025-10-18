package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGiveMessages extends OkaeriConfig implements GiveMessages {

    @Comment({"# {ITEM} - Name of received item"})
    public Notice itemGivenByAdmin = Notice.chat("<green>► <white>You have received: <green>{ITEM}");

    @Comment({"{ITEM} - the item"})
    public Notice itemGivenToTargetPlayer = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has received <green>{ITEM}");
    public Notice noSpace = Notice.chat("<red>✘ <dark_red>Not enough space in inventory!");
    public Notice itemNotFound = Notice.chat("<green>► <white>Not a valid obtainable item!");
}
