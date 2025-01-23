package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENSignEditorMessages implements SignEditorMessages {
    public Notice noSignFound = Notice.chat("<red>✘ <dark_red>Sign not found, please look at the sign!");
    public Notice invalidIndex = Notice.chat("<red>✘ <dark_red>Invalid index!");
    public Notice lineSet = Notice.chat("<green>► <white>Line <green>{LINE} <white>set to <green>{TEXT}");
}
