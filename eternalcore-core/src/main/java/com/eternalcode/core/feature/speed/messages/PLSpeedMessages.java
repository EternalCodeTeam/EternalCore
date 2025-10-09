package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpeedMessages extends OkaeriConfig implements SpeedMessages {
    
    public Notice speedBetweenZeroAndTen = Notice.chat("<red>✘ <dark_red>Ustaw prędkość w zakresie 0-10!");
    public Notice speedTypeNotCorrect = Notice.chat("<red>✘ <dark_red>Nieprawidłowy typ prędkości");

    @Comment("# {SPEED} - Ustawiona prędkość chodzenia lub latania")
    public Notice speedWalkSet = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia na <green>{SPEED}");
    public Notice speedFlySet = Notice.chat("<green>► <white>Ustawiono prędkość latania na <green>{SPEED}");

    @Comment("# {PLAYER} - Gracz któremu została ustawiona prędkość chodzenia lub latania, {SPEED} - Ustawiona prędkość")
    public Notice speedWalkSetBy = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia gracza <green>{PLAYER} <white>na <green>{SPEED}");
    public Notice speedFlySetBy = Notice.chat("<green>► <white>Ustawiono prędkość latania gracza <green>{PLAYER} <white>na <green>{SPEED}");
}
