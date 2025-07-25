package com.eternalcode.core.feature.troll.demoscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLDemoScreenMessages extends OkaeriConfig implements DemoScreenMessages {

    public Notice demoScreenShownToSelf = Notice.chat("<green>► <white>Pokazałeś ekran demo sobie!</white>");
    public Notice demoScreenShownToPlayer = Notice.chat("<green>► <white>Pokazałeś ekran demo graczowi <green>{PLAYER}!</green>");
}
