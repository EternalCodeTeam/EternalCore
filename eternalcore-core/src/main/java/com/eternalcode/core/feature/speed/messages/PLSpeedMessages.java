package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpeedMessages extends OkaeriConfig implements SpeedMessages {

    Notice invalidSpeedRange = Notice.chat("<red>✘ <dark_red>Ustaw prędkość w zakresie 0-10!");
    Notice invalidSpeedType = Notice.chat("<red>✘ <dark_red>Nieprawidłowy typ prędkości");
    Notice walkSpeedSet = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia na <green>{SPEED}");
    Notice flySpeedSet = Notice.chat("<green>► <white>Ustawiono prędkość latania na <green>{SPEED}");
    Notice walkSpeedSetForTargetPlayer = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia gracza <green>{PLAYER} <white>na <green>{SPEED}");
    Notice flySpeedSetForTargetPlayer = Notice.chat("<green>► <white>Ustawiono prędkość latania gracza <green>{PLAYER} <white>na <green>{SPEED}");
}
