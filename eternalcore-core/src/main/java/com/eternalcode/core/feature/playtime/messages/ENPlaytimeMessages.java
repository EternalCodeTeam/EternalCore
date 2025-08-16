package com.eternalcode.core.feature.playtime.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENPlaytimeMessages extends OkaeriConfig implements PlaytimeMessages {

    public Notice self = Notice.chat("<green>► <white>Your playing time is <green>{PLAYTIME}</green>!</white>");

    public Notice other = Notice.chat("<green>► <white>The playing time of <green>{PLAYER}</green> is <green>{PLAYTIME}</green>!</white>");
}
