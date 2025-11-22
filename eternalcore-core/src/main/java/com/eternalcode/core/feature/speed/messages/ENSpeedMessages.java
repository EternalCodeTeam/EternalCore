package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSpeedMessages extends OkaeriConfig implements SpeedMessages {

    Notice invalidSpeedRange = Notice.chat("<red>✘ <dark_red>Enter speed from 0 to 10!");
    Notice invalidSpeedType = Notice.chat("<red>✘ <dark_red>Invalid speed type!");

    @Comment("# {SPEED} - Walk or fly speed value")
    Notice walkSpeedSet = Notice.chat("<color:#9d6eef>► <white>Walking speed is set to <color:#9d6eef>{SPEED}");
    Notice flySpeedSet = Notice.chat("<color:#9d6eef>► <white>Flying speed is set to <color:#9d6eef>{SPEED}");

    @Comment("# {SPEED} - Target player walk or fly speed value")
    Notice walkSpeedSetForTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Walking speed for <color:#9d6eef>{PLAYER} <white>is set to <color:#9d6eef>{SPEED}");
    Notice flySpeedSetForTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Flying speed for <color:#9d6eef>{PLAYER} <white>is set to <color:#9d6eef>{SPEED}");
}


