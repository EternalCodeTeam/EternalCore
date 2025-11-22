package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLHelpOpMessages extends OkaeriConfig implements HelpOpSection {
    @Comment({"# {PLAYER} - Gracz który wysłał wiadomość na helpop, {TEXT} - Treść wysłanej wiadomości"})
    Notice format =
        Notice.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{PLAYER}<white>: <white>{TEXT}");
    @Comment(" ")
    Notice send = Notice.chat("<color:#9d6eef>► <white>Wiadomość została wysłana do administracji");
    @Comment("# {TIME} - Czas do końca blokady (cooldown)")
    Notice helpOpDelay = Notice.chat("<white>► <red>Możesz użyć tej komendy dopiero za <color:#9d6eef>{TIME}!");
}
