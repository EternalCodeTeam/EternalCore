package com.eternalcode.core.feature.afk.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENAfkMessages extends OkaeriConfig implements AfkMessages {

    @Comment("# {PLAYER} - Player who is in AFK")
    Notice afkOn = Notice.chat("<color:#9d6eef>► <white>{PLAYER} is AFK!");
    Notice afkOff = Notice.chat("<color:#9d6eef>► <white>{PLAYER} is no more AFK!");

    @Comment({" ", "# {TIME} - Time after the player can execute the command."})
    Notice afkDelay = Notice.chat("<red>► <dark_red>You can use this command only after <red>{TIME}!");

    @Comment({" "})
    public String afkKickReason = "<red>You have been kicked due to inactivity!";

    @Comment({" ", "# Placeholder used in %eternalcore_afk_formatted% to indicate AFK status"})
    public String afkEnabledPlaceholder = "<red><b>AFK";
    public String afkDisabledPlaceholder = "";

}
