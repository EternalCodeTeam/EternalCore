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
    Notice neverPlayedBefore = Notice.chat("<color:#9d6eef>{PLAYER} nie grał nigdy na tym serwerze.");

    @Comment("# {PLAYER} - Gracz który ostatnio był widziany na serwerze, {SEEN} - Czas od ostatniego logowania")
    Notice lastSeen =  Notice.chat("<color:#9d6eef>{PLAYER} był ostatnio widziany {SEEN} temu.");

    @Comment("# {PLAYER} - Gracz który jest aktualnie online")
    Notice nowOnline = Notice.chat("<color:#9d6eef>{PLAYER} jest aktualnie online!");

}
