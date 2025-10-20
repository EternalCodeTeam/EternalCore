package com.eternalcode.core.feature.skull.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSkullMessages extends OkaeriConfig implements SkullMessages {

    public Notice playerSkullReceived = Notice.chat("<green>► <white>Player <green>{SKULL} <white>head received");
}

