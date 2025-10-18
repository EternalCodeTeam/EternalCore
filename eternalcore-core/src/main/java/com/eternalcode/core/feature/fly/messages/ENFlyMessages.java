package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENFlyMessages extends OkaeriConfig implements FlyMessages {
    
    @Comment("# {STATE} - Fly status")
    public Notice flyEnabled = Notice.chat("<green>► <white>Fly is now {STATE}");
    public Notice flyDisabled = Notice.chat("<green>► <white>Fly is now {STATE}");

    @Comment("# {STATE} - Target player fly status")
    public Notice flyEnabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
    public Notice flyDisabledForTarget = Notice.chat("<green>► <white>Fly for <green>{PLAYER} <white>is now {STATE}");
}

