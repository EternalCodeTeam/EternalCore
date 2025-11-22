package com.eternalcode.core.feature.fun.demoscreen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLDemoScreenMessages extends OkaeriConfig implements DemoScreenMessages {

    Notice shownToSelf = Notice.chat("<color:#9d6eef>► <white>Pokazałeś ekran demo sobie!</white>");
    Notice shownToOtherPlayer = Notice.chat("<color:#9d6eef>► <white>Pokazałeś ekran demo graczowi <color:#9d6eef>{PLAYER}!</color:#9d6eef>");
}
