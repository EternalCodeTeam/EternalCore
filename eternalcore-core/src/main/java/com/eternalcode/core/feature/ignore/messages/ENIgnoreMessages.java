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
    public Notice ignorePlayer = Notice.chat("<green>► {PLAYER} <white>player has been ignored!");

    public Notice ignoreAll = Notice.chat("<red>► <dark_red>All players have been ignored!");
    public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>You can't ignore yourself!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>You already ignore this player!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been unignored!");

    public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>All players have been unignored!");
    public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>You can't unignore yourself!");

    @Comment("# {PLAYER} - Player name placeholder")
    public Notice notIgnorePlayer =
        Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
