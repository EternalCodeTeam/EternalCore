package com.eternalcode.core.feature.fun.demoscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLDemoScreenMessages extends OkaeriConfig implements DemoScreenMessages {

    public Notice shownToSelf = Notice.chat("<green>► <white>Pokazałeś ekran demo sobie!</white>");
    public Notice shownToOtherPlayer = Notice.chat("<green>► <white>Pokazałeś ekran demo graczowi <green>{PLAYER}!</green>");
}
