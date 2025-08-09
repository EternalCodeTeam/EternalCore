package com.eternalcode.core.feature.fun.demoscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)

public class ENDemoScreenMessages extends OkaeriConfig implements DemoScreenMessages {

    public Notice shownToSelf = Notice.chat("<green>► <white>You have shown the demo screen to yourself!");
    public Notice shownToOtherPlayer = Notice.chat("<green>► <white>You have shown the demo screen to player <green>{PLAYER}!");
}
