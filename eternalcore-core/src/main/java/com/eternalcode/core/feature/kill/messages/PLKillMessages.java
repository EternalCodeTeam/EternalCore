package com.eternalcode.core.feature.kill.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLKillMessages extends OkaeriConfig implements KillMessages {
    Notice killedYourself = Notice.chat("<red>✘ <dark_red>Zabiłeś się!");
    Notice killedTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Zabito gracza <color:#9d6eef>{PLAYER}");
    Notice killedByAdmin = Notice.chat("<red>✘ <dark_red>Administrator cię zabił!");
}
