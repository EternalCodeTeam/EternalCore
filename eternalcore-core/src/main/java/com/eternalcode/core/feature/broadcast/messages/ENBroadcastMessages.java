package com.eternalcode.core.feature.broadcast.messages;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBroadcastMessages extends OkaeriConfig implements BroadcastMessages {

    @Comment("# {BROADCAST} - Broadcast")
    public String messageFormat = "<color:#9d6eef><bold>BROADCAST:</bold> <white>{BROADCAST}";

    @Comment("# Used only in title broadcasts")
    public String titleHeader = "<color:#9d6eef><bold>BROADCAST:</bold>";

}
