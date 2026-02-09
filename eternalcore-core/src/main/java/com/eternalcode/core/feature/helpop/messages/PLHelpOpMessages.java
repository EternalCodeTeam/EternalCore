package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class PLHelpOpMessages extends OkaeriConfig implements HelpOpSection {
    @Comment({ "# {PLAYER} - Gracz który wysłał wiadomość na helpop, {TEXT} - Treść wysłanej wiadomości" })
    Notice format = Notice
            .chat("<dark_gray>[<color:#9d6eef>HelpOp<dark_gray>] <white>{PLAYER}<dark_gray>: <white>{TEXT}");
    @Comment(" ")
    Notice send = Notice.chat("<color:#9d6eef>► <white>Wiadomość została wysłana do administracji");
    @Comment("# {TIME} - Czas do końca blokady (cooldown)")
    Notice helpOpDelay = Notice.chat("<red>✘ <dark_red>Możesz użyć tej komendy dopiero za <red>{TIME}<dark_red>!");
    @Comment("# {ADMIN} - Admin który odpowiedział, {PLAYER} - Gracz który otrzymał odpowiedź, {TEXT} - Treść odpowiedzi")
    Notice adminReply = BukkitNotice.builder()
        .chat("<dark_gray>[<color:#9d6eef>HelpOp<dark_gray>] <white>{ADMIN} <dark_gray>-> <white>{PLAYER}<dark_gray>: <white>{TEXT}")
        .sound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f)
        .build();
    @Comment(" ")
    Notice adminReplySend = Notice.chat("<color:#9d6eef>► <white>Odpowiedź została wysłana do gracza <color:#9d6eef>{PLAYER}");
    @Comment("# {PLAYER} - Nazwa gracza")
    Notice playerNotSentHelpOp = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie wysłał żadnego zapytania na helpop");
}
