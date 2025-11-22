package com.eternalcode.core.feature.fun.elderguardian.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENElderGuardianMessages extends OkaeriConfig implements ElderGuardianMessages {

    Notice elderGuardianShown =
        Notice.chat("<color:#9d6eef>► <white>Shown elder guardian to player <color:#9d6eef>{PLAYER}<white>!");
    Notice elderGuardianShownSilently =
        Notice.chat("<color:#9d6eef>► <white>Shown elder guardian to player <color:#9d6eef>{PLAYER}<white> silently!");
}
