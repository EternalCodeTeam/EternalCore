package com.eternalcode.core.feature.burn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBurnMessages extends OkaeriConfig implements BurnMessages {

    @Comment("# Available placeholders: {PLAYER} - burned player, {TICKS} - number of ticks the player is on fire")
    Notice burnedSelf = Notice.chat("<color:#9d6eef>► <white>You have been set on fire for <color:#9d6eef>{TICKS}<white> ticks!");

    @Comment(" ")
    Notice burnedOther = Notice.chat("<color:#9d6eef>► {PLAYER} <white>has been set on fire for <color:#9d6eef>{TICKS} <white>ticks!");
}
