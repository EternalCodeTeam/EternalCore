package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLHelpOpMessages implements HelpOpSection {
    @Description({"# {PLAYER} - Gracz który wysłał wiadomość na helpop, {TEXT} - Treść wysłanej wiadomości"})
    public Notice format =
        Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<white>: <white>{TEXT}");
    @Description(" ")
    public Notice send = Notice.chat("<green>► <white>Wiadomość została wysłana do administracji");
    @Description("# {TIME} - Czas do końca blokady (cooldown)")
    public Notice helpOpDelay = Notice.chat("<white>► <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
}
