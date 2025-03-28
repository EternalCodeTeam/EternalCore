package com.eternalcode.core.feature.seen.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLSeenMessages implements SeenMessages {

    @Description("# {PLAYER} - Gracz który nigdy nie grał na serwerze")
    public Notice neverPlayedBefore = Notice.chat("<green>{PLAYER} nie grał nigdy na tym serwerze.");

    @Description("# {PLAYER} - Gracz który ostatnio był widziany na serwerze, {SEEN} - Czas od ostatniego logowania")
    public Notice lastSeen =  Notice.chat("<green>{PLAYER} był ostatnio widziany {SEEN} temu.");

    @Description("# {PLAYER} - Gracz który jest aktualnie online")
    public Notice nowOnline = Notice.chat("<green>{PLAYER} jest aktualnie online!");

}
