package com.eternalcode.core.feature.notarget.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLNoTargetMessages extends OkaeriConfig implements NoTargetMessages {
    Notice disabled = Notice.chat("<color:#9d6eef>► <white>Potwory nie będą Cię więcej ignorować!");
    Notice enabled = Notice.chat("<color:#9d6eef>► <white>Potwory będą Cię ignorować!");

    @Comment({ " ", "# Placeholder: {PLAYER} - Wybrany komendą gracz"})
    Notice turnedOff = Notice.chat("<color:#9d6eef>► <white>Potwory nie będą ignorować gracza: <green>{PLAYER}<white> - ochrona została wyłączona!");
    Notice turnedOn = Notice.chat("<color:#9d6eef>► <white>Potwory będą ignorować gracza: <green>{PLAYER}<white> - ochrona została włączona!");
}
