package com.eternalcode.core.feature.gamemode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGameModeMessages extends OkaeriConfig implements GameModeMessages {
    
    Notice gamemodeTypeInvalid = Notice.chat("<red>✘ <dark_red>Not a valid gamemode type");

    @Comment("# {GAMEMODE} - Gamemode name")
    Notice gamemodeSet = Notice.chat("<color:#9d6eef>► <white>Gamemode now is set to: <color:#9d6eef>{GAMEMODE}");

    @Comment("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
    Notice gamemodeSetToTarget = Notice.chat("<color:#9d6eef>► <white>Gamemode for <color:#9d6eef>{PLAYER} <white>now is set to: <color:#9d6eef>{GAMEMODE}");
}

