package com.eternalcode.core.feature.afk.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLAfkMessages implements AfkMessages {

    @Description("# {PLAYER} - Gracz ")
    public Notice afkOn = Notice.chat("<green>► <white>{PLAYER} jest AFK!");

    @Description("# {PLAYER} - Gracz ")
    public Notice afkOff = Notice.chat("<green>► <white>{PLAYER} już nie jest AFK!");

    @Description({" ", "# {TIME} - Czas po którym gracz może użyć komendy"})
    public Notice afkDelay = Notice.chat("<red>► <dark_red>Możesz użyć tej komendy dopiero po <dark_red>{TIME}!");

    @Description({" "})
    public String afkKickReason = "<red>Zostałeś wyrzucony z powodu braku aktywności!";

    @Description({" ", "# Używane w %eternalcore_afk_formatted% do wskazania statusu AFK"})
    public String afkEnabledPlaceholder = "<red><b>AFK";

    public String afkDisabledPlaceholder = "";
}
