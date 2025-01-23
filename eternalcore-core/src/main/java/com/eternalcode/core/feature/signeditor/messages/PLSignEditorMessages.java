package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLSignEditorMessages implements SignEditorMessages{
    public Notice noSignFound = Notice.chat("<red>✘ <dark_red>Nie odnaleziono tabliczki, proszę spojrzeć na tabliczkę!");
    public Notice invalidIndex = Notice.chat("<red>✘ <dark_red>Nieprawidłowy indeks!");
    public Notice lineSet = Notice.chat("<green>► <white>Ustawiono linię <green>{LINE} <white>na <green>{TEXT}");
}
