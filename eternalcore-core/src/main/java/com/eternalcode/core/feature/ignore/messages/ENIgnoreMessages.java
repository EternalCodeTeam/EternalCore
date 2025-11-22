package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENIgnoreMessages extends OkaeriConfig implements IgnoreMessages {
    Notice playerIgnored = Notice.chat("<color:#9d6eef>► {PLAYER} <white>player has been ignored!");
    Notice allPlayersIgnored = Notice.chat("<red>► <dark_red>All players have been ignored!");
    Notice cannotIgnoreSelf = Notice.chat("<red>► <dark_red>You can't ignore yourself!");
    Notice playerAlreadyIgnored = Notice.chat("<red>► <dark_red>You already ignore this player!");
    Notice playerUnignored = Notice.chat("<red>► <dark_red>{PLAYER} <red>player has been unignored!");
    Notice allPlayersUnignored = Notice.chat("<red>► <dark_red>All players have been unignored!");
    Notice cannotUnignoreSelf = Notice.chat("<red>► <dark_red>You can't unignore yourself!");
    Notice playerNotIgnored = Notice.chat("<red>► <dark_red>You don't ignore this player, so you can unignore him!");
}
