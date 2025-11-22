package com.eternalcode.core.feature.playtime.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENPlaytimeMessages extends OkaeriConfig implements PlaytimeMessages {

    Notice self = Notice.chat("<color:#9d6eef>► <white>Your playing time is <color:#9d6eef>{PLAYTIME}</color:#9d6eef>!</white>");

    Notice other = Notice.chat("<color:#9d6eef>► <white>The playing time of <color:#9d6eef>{PLAYER}</color:#9d6eef> is <color:#9d6eef>{PLAYTIME}</color:#9d6eef>!</white>");
}
