package com.eternalcode.core.feature.seen.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENSeenMessages implements SeenMessages {

    @Description("# {PLAYER} - The player who is never played before on the server")
    public Notice neverPlayedBefore = Notice.chat("<green>{PLAYER} has not played before on this server.");

    @Description("# {PLAYER} - The player who was last seen on the server, {SEEN} - Time since last login")
    public Notice lastSeen =  Notice.chat("<green>{PLAYER} was last seen {SEEN} ago.");

    @Description("# {PLAYER} - The player who is now online")
    public Notice nowOnline = Notice.chat("<green>{PLAYER} is now online!");

}
