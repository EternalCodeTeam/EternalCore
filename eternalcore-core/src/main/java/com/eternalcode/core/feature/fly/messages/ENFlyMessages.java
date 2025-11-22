package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFlyMessages extends OkaeriConfig implements FlyMessages {
    Notice flyEnabled = Notice.chat("<color:#9d6eef>► <white>Fly is now {STATE}");
    Notice flyDisabled = Notice.chat("<color:#9d6eef>► <white>Fly is now {STATE}");
    Notice flyEnabledForTarget = Notice.chat("<color:#9d6eef>► <white>Fly for <color:#9d6eef>{PLAYER} <white>is now {STATE}");
    Notice flyDisabledForTarget = Notice.chat("<color:#9d6eef>► <white>Fly for <color:#9d6eef>{PLAYER} <white>is now {STATE}");
}

