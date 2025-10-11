package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSpeedMessages extends OkaeriConfig implements SpeedMessages {

    public Notice invalidRange = Notice.chat("<red>✘ <dark_red>Enter speed from 0 to 10!");
    public Notice invalidType = Notice.chat("<red>✘ <dark_red>Invalid speed type!");

    @Comment("# {SPEED} - Walk or fly speed value")
    public Notice walkSet = Notice.chat("<green>► <white>Walking speed is set to <green>{SPEED}");
    public Notice flySet = Notice.chat("<green>► <white>Flying speed is set to <green>{SPEED}");

    @Comment("# {PLAYER} - Target player, {SPEED} - Target player walk or fly speed value")
    public Notice walkSetFor = Notice.chat("<green>► <white>Walking speed for <green>{PLAYER} <white>is set to <green>{SPEED}");
    public Notice flySetFor = Notice.chat("<green>► <white>Flying speed for <green>{PLAYER} <white>is set to <green>{SPEED}");
}


