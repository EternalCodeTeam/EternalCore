package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFlyMessages extends OkaeriConfig implements FlyMessages {
    public Notice flyEnabled = Notice.chat("<green>► <white>Fly is now {STATE}");
    public Notice flyDisabled = Notice.chat("<green>► <white>Fly is now {STATE}");
    public Notice flyEnabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
    public Notice flyDisabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
}

