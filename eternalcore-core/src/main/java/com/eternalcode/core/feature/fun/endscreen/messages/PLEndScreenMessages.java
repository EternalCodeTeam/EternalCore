package com.eternalcode.core.feature.fun.endscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLEndScreenMessages extends OkaeriConfig implements EndScreenMessages {

    Notice shownToSelf = Notice.chat("<color:#9d6eef>► <white>Pokazałeś ekran końca gry sobie!");

    @Comment("# {PLAYER} - ten placeholder zostanie zastąpiony przez nazwę gracza, któremu pokazujesz ekran końca gry.")
    Notice shownToOtherPlayer = Notice
            .chat("<color:#9d6eef>► <white>Pokazałeś ekran końca gry graczowi <color:#9d6eef>{PLAYER}!");
}
