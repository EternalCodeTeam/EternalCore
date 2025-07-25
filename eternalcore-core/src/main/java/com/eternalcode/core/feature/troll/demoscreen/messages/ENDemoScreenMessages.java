package com.eternalcode.core.feature.troll.demoscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)

public class ENDemoScreenMessages extends OkaeriConfig implements DemoScreenMessages {

    public Notice demoScreenShownToSelf = Notice.chat("<green>► <white>You have shown the demo screen to yourself!");
    public Notice demoScreenShownToPlayer = Notice.chat("<green>► <white>You have shown the demo screen to player <green>{PLAYER}!");
}
