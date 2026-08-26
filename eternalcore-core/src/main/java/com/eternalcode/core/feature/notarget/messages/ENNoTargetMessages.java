package com.eternalcode.core.feature.notarget.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENNoTargetMessages extends OkaeriConfig implements NoTargetMessages {
    Notice disabled = Notice.chat("<color:#9d6eef>► <white>Monsters will target You!");
    Notice enabled = Notice.chat("<color:#9d6eef>► <white>Monsters will no longer target You!");

    @Comment({" ", "# Placeholder: {PLAYER} - Targeted player name"})
    Notice turnedOff = Notice.chat("<color:#9d6eef>► <white>Monsters will target <green>{PLAYER}<white> - protection has been turned off!");
    Notice turnedOn = Notice.chat("<color:#9d6eef>► <white>Monsters will ignore <green>{PLAYER}<white> - protection has been turned on!");

}
