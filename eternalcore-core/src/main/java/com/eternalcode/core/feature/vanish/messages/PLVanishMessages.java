package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLVanishMessages extends OkaeriConfig implements VanishMessages {

    public Notice vanishEnabled = Notice.chat("<green>► <white>Włączono tryb niewidoczności!");
    public Notice vanishDisabled = Notice.chat("<red>► <white>Wyłączono tryb niewidoczności!");

    public Notice vanishEnabledOther = Notice.chat("<green>► <white>{PLAYER} włączył tryb niewidoczności!");
    public Notice vanishDisabledOther = Notice.chat("<red>► <white>{PLAYER} wyłączył tryb niewidoczności!");

    public Notice joinedInVanishMode = Notice.chat("<green>► <white>Dołączyłeś do serwera w trybie niewidoczności.");
    public Notice playerJoinedInVanishMode = Notice.chat("<green>► <white>{PLAYER} dołączył do serwera w trybie niewidoczności.");

}
