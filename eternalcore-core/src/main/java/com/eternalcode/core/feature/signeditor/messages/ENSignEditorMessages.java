package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSignEditorMessages extends OkaeriConfig implements SignEditorMessages {
    public Notice noSignFound = Notice.chat("<red>✘ <dark_red>Look at the sign to edit it!");
    public Notice invalidIndex = Notice.chat("<red>✘ <dark_red>The value {LINE} is invalid! <red>Please select a number between 1 and 4!");
    public Notice lineSet = Notice.chat("<green>► <white>Line <green>{LINE} <white>set to <green>{TEXT}");
}
