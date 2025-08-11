package com.eternalcode.core.feature.fun.endscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLEndScreenMessages extends OkaeriConfig implements EndScreenMessages {

    public Notice shownToSelf = Notice.chat("<green>► <white>Pokazałeś ekran końca gry sobie!</white>");

    @Comment("# {PLAYER} - ten placeholder zostanie zastąpiony przez nazwę gracza, któremu pokazujesz ekran końca gry.")
    public Notice shownToOtherPlayer = Notice.chat("<green>► <white>Pokazałeś ekran końca gry graczowi <green>{PLAYER}!</green>");
}
