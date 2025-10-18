package com.eternalcode.core.feature.broadcast.messages;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLBroadcastMessages extends OkaeriConfig implements BroadcastMessages {

    @Comment("# {BROADCAST} - Ogłoszenie")
    public String messageFormat = "<red><bold>OGŁOSZENIE:</bold> <gray>{BROADCAST}";

    @Comment("# Używane tylko w ogłoszeniach tytułowych")
    public String titleHeader = "<red><bold>OGŁOSZENIE:</bold>";

}
