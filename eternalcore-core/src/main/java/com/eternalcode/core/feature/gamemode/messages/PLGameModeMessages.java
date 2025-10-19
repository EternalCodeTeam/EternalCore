package com.eternalcode.core.feature.gamemode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGameModeMessages extends OkaeriConfig implements GameModeMessages {

    public Notice gamemodeTypeInvalid = Notice.chat("<red>✘ <dark_red>Niepoprawny typ!");
    public Notice gamemodeSet = Notice.chat("<green>► <white>Ustawiono tryb gry na: <green>{GAMEMODE}");
    public Notice gamemodeSetToTarget = Notice.chat("<green>► <white>Ustawiono tryb gry graczowi <green>{PLAYER} <white>na: <green>{GAMEMODE}");
}

