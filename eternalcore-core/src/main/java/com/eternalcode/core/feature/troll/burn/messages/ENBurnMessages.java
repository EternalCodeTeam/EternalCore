package com.eternalcode.core.feature.troll.burn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBurnMessages extends OkaeriConfig implements BurnMessages {

    @Comment(" ")
    public Notice burnedSelf = Notice.chat("<green>► <white>You have been set on fire for <green>{TICKS}<white> ticks!");

    @Comment(" ")
    public Notice burnedOther = Notice.chat("<green>► {PLAYER} <white>has been set on fire for <green>{TICKS} <white>ticks!");
}
