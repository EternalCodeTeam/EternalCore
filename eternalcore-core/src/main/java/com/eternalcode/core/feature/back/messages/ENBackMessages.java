package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBackMessages extends OkaeriConfig implements BackMessages {

    public Notice lastLocationNoExist = Notice.chat("<red>► <white>You don't have any last location to teleport to!");
    public Notice teleportedToLastLocation = Notice.chat("<green>► <white>You have been teleported to your last location!");
    public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Player <green>{PLAYER} <white>has been teleported to their last location!");

}
