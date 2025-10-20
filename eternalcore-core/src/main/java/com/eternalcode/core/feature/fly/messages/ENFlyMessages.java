package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFlyMessages extends OkaeriConfig implements FlyMessages {
    Notice flyEnabled = Notice.chat("<green>► <white>Fly is now {STATE}");
    Notice flyDisabled = Notice.chat("<green>► <white>Fly is now {STATE}");
    Notice flyEnabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
    Notice flyDisabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
}

