package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENVanishMessages extends OkaeriConfig implements VanishMessages {

    public Notice vanishEnabled = Notice.chat("<green>► <white>Vanish mode enabled!");
    public Notice vanishDisabled = Notice.chat("<red>► <white>Vanish mode disabled!");

    public Notice vanishEnabledOther = Notice.chat("<green>► <white>{PLAYER} has enabled vanish mode!");
    public Notice vanishDisabledOther = Notice.chat("<red>► <white>{PLAYER} has disabled vanish mode!");

    public Notice joinedInVanishMode = Notice.chat("<green>► <white>You have joined the server in vanish mode.");
    public Notice playerJoinedInVanishMode = Notice.chat("<green>► <white>{PLAYER} has joined the server in vanish mode.");

}
