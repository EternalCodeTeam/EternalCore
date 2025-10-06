package com.eternalcode.core.feature.seen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENSeenMessages extends OkaeriConfig implements SeenMessages {

    @Comment("# {PLAYER} - The player who is never played before on the server")
    Notice neverPlayedBefore = Notice.chat("<green>{PLAYER} has not played before on this server.");

    @Comment("# {PLAYER} - The player who was last seen on the server, {SEEN} - Time since last login")
    Notice lastSeen =  Notice.chat("<green>{PLAYER} was last seen {SEEN} ago.");

    @Comment("# {PLAYER} - The player who is now online")
    Notice nowOnline = Notice.chat("<green>{PLAYER} is now online!");

}
