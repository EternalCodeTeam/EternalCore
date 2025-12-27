package com.eternalcode.core.feature.signeditor.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSignEditorMessages extends OkaeriConfig implements SignEditorMessages {
    Notice noSignFound = Notice.chat("<red>✘ <dark_red>Spójrz na tabliczkę, aby ją edytować!");
    Notice invalidIndex = Notice.chat("<red>✘ <dark_red>Wartość {LINE} jest nieprawidłowa! <red>Wybierz numer od 1 do 4!");
    Notice lineSet = Notice.chat("<color:#9d6eef>► <white>Zmieniono linię <color:#9d6eef>{LINE}</color:#9d6eef> na <color:#9d6eef>{TEXT}");
}
