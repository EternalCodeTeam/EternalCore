package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLSignEditorMessages implements SignEditorMessages {
    public Notice noSignFound = Notice.chat("<red>✘ <dark_red>Spójrz na tabliczkę, aby ją edytować!");
    public Notice invalidIndex = Notice.chat("<red>✘ <dark_red>Wartość {LINE} jest nieprawidłowa! <red>Wybierz numer od 1 do 4!");
    public Notice lineSet = Notice.chat("<green>► <white>Wartość <green>{LINE}</green> została ustawiona na <green>{TEXT}");
}
