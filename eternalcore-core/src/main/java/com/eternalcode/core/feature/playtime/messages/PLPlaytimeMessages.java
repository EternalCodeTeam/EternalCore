package com.eternalcode.core.feature.playtime.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPlaytimeMessages extends OkaeriConfig implements PlaytimeMessages {

    Notice self = Notice.chat("<green>► <white>Twój czas gry wynosi <green>{PLAYTIME}</green>!</white>");

    Notice other = Notice.chat("<green>► <white>Czas gry gracza <green>{PLAYER}</green> wynosi <green>{PLAYTIME}</green>!</white>");
}
