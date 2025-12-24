package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSignEditorMessages extends OkaeriConfig implements SignEditorMessages {
    Notice noSignFound = Notice.chat("<red>✘ <dark_red>Look at the sign to edit it!");
    Notice invalidIndex = Notice.chat("<red>✘ <dark_red>The value {LINE} is invalid! <red>Please select a number between 1 and 4!");
    Notice lineSet = Notice.chat("<color:#9d6eef>► <white>Line <color:#9d6eef>{LINE} <white>set to <color:#9d6eef>{TEXT}");
}
