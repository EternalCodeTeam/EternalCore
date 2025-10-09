package com.eternalcode.core.feature.gamemode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGameModeMessages extends OkaeriConfig implements GameModeMessages {
    
    public Notice gameModeNotCorrect = Notice.chat("<red>✘ <dark_red>Niepoprawny typ!");
    
    @Comment("# {GAMEMODE} - Ustawiony tryb gry")
    public Notice gameModeMessage = Notice.chat("<green>► <white>Ustawiono tryb gry na: <green>{GAMEMODE}");
    
    @Comment("# {PLAYER} - Gracz któremu został ustawiony tryb gry, {GAMEMODE} - Ustawiony tryb gry dla gracza")
    public Notice gameModeSetMessage = Notice.chat("<green>► <white>Ustawiono tryb gry graczowi <green>{PLAYER} <white>na: <green>{GAMEMODE}");
}
