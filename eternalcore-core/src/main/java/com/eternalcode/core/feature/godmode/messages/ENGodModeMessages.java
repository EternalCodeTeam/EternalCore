package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENGodModeMessages extends OkaeriConfig implements GodModeMessages {
    
    @Comment({" ", "# {STATE} - Godmode status"})
    public Notice godEnable = Notice.chat("<green>► <white>God is now {STATE}");
    public Notice godDisable = Notice.chat("<green>► <white>God is now {STATE}");

    @Comment({" ", "# {PLAYER} - Target player, {STATE} - Target player godmode status"})
    public Notice godSetEnable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");
    public Notice godSetDisable = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>god is now: {STATE}");
}
