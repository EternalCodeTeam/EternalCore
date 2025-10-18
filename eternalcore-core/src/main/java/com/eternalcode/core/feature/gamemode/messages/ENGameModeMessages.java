package com.eternalcode.core.feature.gamemode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGameModeMessages extends OkaeriConfig implements GameModeMessages {
    
    public Notice gamemodeTypeInvalid = Notice.chat("<red>✘ <dark_red>Not a valid gamemode type");

    @Comment("# {GAMEMODE} - Gamemode name")
    public Notice gamemodeSet = Notice.chat("<green>► <white>Gamemode now is set to: <green>{GAMEMODE}");

    @Comment("# {PLAYER} - Target player, {GAMEMODE} - Gamemode")
    public Notice gamemodeSetToTarget = Notice.chat("<green>► <white>Gamemode for <green>{PLAYER} <white>now is set to: <green>{GAMEMODE}");
}

