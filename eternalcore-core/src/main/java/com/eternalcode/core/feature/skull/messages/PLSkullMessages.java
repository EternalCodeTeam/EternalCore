package com.eternalcode.core.feature.skull.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSkullMessages extends OkaeriConfig implements SkullMessages {

    public Notice playerSkullReceived = Notice.chat("<green>► <white>Otrzymałeś głowę gracza: <green>{SKULL}");
}
