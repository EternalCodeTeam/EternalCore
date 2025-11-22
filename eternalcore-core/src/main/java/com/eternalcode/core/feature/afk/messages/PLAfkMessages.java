package com.eternalcode.core.feature.afk.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLAfkMessages extends OkaeriConfig implements AfkMessages {

    @Comment("# {PLAYER} - Gracz ")
    Notice afkOn = Notice.chat("<color:#9d6eef>► <white>{PLAYER} jest AFK!");

    @Comment("# {PLAYER} - Gracz ")
    Notice afkOff = Notice.chat("<color:#9d6eef>► <white>{PLAYER} już nie jest AFK!");

    @Comment({" ", "# {TIME} - Czas po którym gracz może użyć komendy"})
    Notice afkDelay = Notice.chat("<red>► <dark_red>Możesz użyć tej komendy dopiero po <dark_red>{TIME}!");

    @Comment({" "})
    public String afkKickReason = "<red>Zostałeś wyrzucony z powodu braku aktywności!";

    @Comment({" ", "# Używane w %eternalcore_afk_formatted% do wskazania statusu AFK"})
    public String afkEnabledPlaceholder = "<red><b>AFK";

    public String afkDisabledPlaceholder = "";
}
