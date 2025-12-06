package com.eternalcode.core.feature.gamemode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGameModeMessages extends OkaeriConfig implements GameModeMessages {

    Notice gamemodeTypeInvalid = Notice.chat("<red>✘ <dark_red>Niepoprawny typ!");
    Notice gamemodeSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono tryb gry na: <color:#9d6eef>{GAMEMODE}");
    Notice gamemodeSetToTarget = Notice.chat(
            "<color:#9d6eef>► <white>Ustawiono tryb gry graczowi <color:#9d6eef>{PLAYER} <white>na: <color:#9d6eef>{GAMEMODE}");
}
