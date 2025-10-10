package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENIgnoreMessages extends OkaeriConfig implements IgnoreMessages {

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice playerIgnored = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

    public Notice allPlayersIgnored = Notice.chat("<red>► <dark_red>All players have been ignored!");

    public Notice cannotIgnoreSelf = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice playerAlreadyIgnored = Notice.chat("<red>► <dark_red>You already ignore this player!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice playerUnignored = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been unignored!");

    public Notice allPlayersUnignored = Notice.chat("<red>► <dark_red>All players have been unignored!");

    public Notice cannotUnignoreSelf = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice playerNotIgnored =
        Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
