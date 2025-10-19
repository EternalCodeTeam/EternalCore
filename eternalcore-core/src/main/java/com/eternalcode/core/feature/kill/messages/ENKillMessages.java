package com.eternalcode.core.feature.kill.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENKillMessages extends OkaeriConfig implements KillMessages {
    public Notice killedYourself = Notice.chat("<red>► <dark_red>You have been killed!");
    public Notice killedTargetPlayer = Notice.chat("<red>► <dark_red>Killed <red>{PLAYER}");
    public Notice killedByAdmin = Notice.chat("<red>► <dark_red>You have been killed by admin!");
}

