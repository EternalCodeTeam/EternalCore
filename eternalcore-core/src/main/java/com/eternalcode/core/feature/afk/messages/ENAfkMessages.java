package com.eternalcode.core.feature.afk.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENAfkMessages implements AfkMessages {

    @Description("# {PLAYER} - Player who is in AFK")
    public Notice afkOn = Notice.chat("<green>► <white>{PLAYER} is AFK!");
    public Notice afkOff = Notice.chat("<green>► <white>{PLAYER} is no more AFK!");

    @Description({" ", "# {TIME} - Time after the player can execute the command."})
    public Notice afkDelay = Notice.chat("<red>► <dark_red>You can use this command only after <red>{TIME}!");

    @Description({" "})
    public String afkKickReason = "<red>You have been kicked due to inactivity!";

    @Description({" ", "# Placeholder used in %eternalcore_afk_formatted% to indicate AFK status"})
    public String afkEnabledPlaceholder = "<red><b>AFK";
    public String afkDisabledPlaceholder = "";

}
