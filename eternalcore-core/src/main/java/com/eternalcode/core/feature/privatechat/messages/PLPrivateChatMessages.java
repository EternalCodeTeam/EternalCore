package com.eternalcode.core.feature.privatechat.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLPrivateChatMessages implements PrivateChatMessages {
    public Notice noReply = Notice.chat(
        "<red>► <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

    @Description("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
    public Notice privateMessageYouToTarget =
        Notice.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description({" ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości"})
    public Notice privateMessageTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
    public Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Description(" ")
    public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy został {STATE}<white>!");
    public Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy został {STATE}<white>!");

    public Notice receiverDisabledMessages = Notice.chat("<red>► <dark_red>Wiadomości prywatne zostały wyłączone!");

    public Notice selfMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone!");
    public Notice selfMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone!");
    public Notice selfMessagesError = Notice.chat("<red>► <white>Nie możesz wysyłać wiadomości prywatnych do samego siebie!");

    @Description({" ", "# {PLAYER} - Gracz któremu wyłączono wiadomości prywatne"})
    public Notice otherMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone <white>dla gracza <green>{PLAYER}<white>!");
    public Notice otherMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone <white>dla gracza <green>{PLAYER}<white>!");

    @Description({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    public Notice ignorePlayer = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

    @Description(" ")
    public Notice ignoreAll = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
    public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

    @Description({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

    @Description({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

    @Description(" ")
    public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");
    public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

    @Description({" ", "# {PLAYER} - Gracz który jest zignorowany"})
    public Notice notIgnorePlayer = Notice.chat(
        "<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
}
