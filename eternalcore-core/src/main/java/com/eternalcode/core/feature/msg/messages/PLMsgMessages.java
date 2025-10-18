package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLMsgMessages extends OkaeriConfig implements MsgMessages {
    Notice noReply = Notice.chat(
        "<red>► <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

    @Comment("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
    Notice msgYouToTarget =
        Notice.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({" ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości"})
    Notice msgTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
    Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment(" ")
    Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy został {STATE}<white>!");
    Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy został {STATE}<white>!");

    Notice receiverDisabledMessages = Notice.chat("<red>► <dark_red>Wiadomości prywatne zostały wyłączone!");

    Notice selfMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone!");
    Notice selfMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone!");

    @Comment({" ", "# {PLAYER} - Gracz któremu wyłączono wiadomości prywatne"})
    Notice otherMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone <white>dla gracza <green>{PLAYER}<white>!");
    Notice otherMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone <white>dla gracza <green>{PLAYER}<white>!");

    @Comment({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    Notice ignorePlayer = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

    @Comment(" ")
    Notice ignoreAll = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
    Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

    @Comment({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

    @Comment({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

    @Comment(" ")
    Notice unIgnoreAll = Notice.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");
    Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

    @Comment({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    Notice notIgnorePlayer = Notice.chat(
        "<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
}
