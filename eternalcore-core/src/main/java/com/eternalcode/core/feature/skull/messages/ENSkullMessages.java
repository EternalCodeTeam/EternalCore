package com.eternalcode.core.feature.skull.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSkullMessages extends OkaeriConfig implements SkullMessages {

    Notice playerSkullReceived = Notice.chat("<color:#9d6eef>► <white>Player <color:#9d6eef>{SKULL} <white>head received");
}

