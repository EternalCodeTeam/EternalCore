package com.eternalcode.core.feature.fun.elderguardian.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENElderGuardianMessages extends OkaeriConfig implements ElderGuardianMessages {

    Notice elderGuardianShown =
        Notice.chat("<green>► <white>Shown elder guardian to player <green>{PLAYER}<white>!");
    Notice elderGuardianShownSilently =
        Notice.chat("<green>► <white>Shown elder guardian to player <green>{PLAYER}<white> silently!");
}
