package com.eternalcode.core.feature.seen.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSeenMessages extends OkaeriConfig implements SeenMessages {

    @Comment("# {PLAYER} - Gracz który nigdy nie grał na serwerze")
    public Notice neverPlayedBefore = Notice.chat("<green>{PLAYER} nie grał nigdy na tym serwerze.");

    @Comment("# {PLAYER} - Gracz który ostatnio był widziany na serwerze, {SEEN} - Czas od ostatniego logowania")
    public Notice lastSeen =  Notice.chat("<green>{PLAYER} był ostatnio widziany {SEEN} temu.");

    @Comment("# {PLAYER} - Gracz który jest aktualnie online")
    public Notice nowOnline = Notice.chat("<green>{PLAYER} jest aktualnie online!");

}
