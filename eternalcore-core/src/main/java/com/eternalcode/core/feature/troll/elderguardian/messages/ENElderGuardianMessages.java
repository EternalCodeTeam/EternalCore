package com.eternalcode.core.feature.troll.elderguardian.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENElderGuardianMessages implements ElderGuardianMessages {

    public Notice elderGuardianShown =
        Notice.chat("<green>► <white>Shown elder guardian to player <green>{PLAYER}<white>!");
    public Notice elderGuardianShownSilently =
        Notice.chat("<green>► <white>Shown elder guardian to player <green>{PLAYER}<white> silently!");
}
