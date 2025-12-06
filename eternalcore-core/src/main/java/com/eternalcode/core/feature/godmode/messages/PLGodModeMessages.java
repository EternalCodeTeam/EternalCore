package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGodModeMessages extends OkaeriConfig implements GodModeMessages {
    Notice godModeEnabled = Notice.chat("<color:#9d6eef>► <white>Tryb nieśmiertelności został {STATE}");
    Notice godModeDisabled = Notice.chat("<color:#9d6eef>► <white>Tryb nieśmiertelności został {STATE}");
    Notice godModeEnabledForTarget = Notice.chat("<color:#9d6eef>► <white>Tryb nieśmiertelności dla gracza <color:#9d6eef>{PLAYER} <white>został {STATE}");
    Notice godModeDisabledForTarget = Notice.chat("<color:#9d6eef>► <white>Tryb nieśmiertelności dla gracza <color:#9d6eef>{PLAYER} <white>został {STATE}");
}

