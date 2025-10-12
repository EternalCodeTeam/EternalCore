package com.eternalcode.core.feature.msg.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLMsgMessages extends OkaeriConfig implements MsgMessages {
    public Notice noReply = Notice.chat(
        "<red>► <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

    @Comment("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
    public Notice msgYouToTarget =
        Notice.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({" ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości"})
    public Notice msgTargetToYou =
        Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
    public Notice socialSpyMessage = Notice.chat(
        "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment(" ")
    public Notice socialSpyEnable = Notice.chat("<green>► <white>SocialSpy został {STATE}<white>!");
    public Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy został {STATE}<white>!");

    public Notice receiverDisabledMessages = Notice.chat("<red>► <dark_red>Wiadomości prywatne zostały wyłączone!");

    public Notice selfMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone!");
    public Notice selfMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone!");

    @Comment({" ", "# {PLAYER} - Gracz któremu wyłączono wiadomości prywatne"})
    public Notice otherMessagesDisabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <red>wyłączone <white>dla gracza <green>{PLAYER}<white>!");
    public Notice otherMessagesEnabled = Notice.chat("<green>► <white>Wiadomości prywatne zostały <green>włączone <white>dla gracza <green>{PLAYER}<white>!");

    @Comment("# Formatowanie placeholderów")
    public PLPlaceholders placeholders = new PLPlaceholders();

    @Getter
    @Accessors(fluent = true)
    public static class PLPlaceholders implements MsgMessages.Placeholders {

        private String msgEnabled = "<green>Włączone";
        private String msgDisabled = "<red>Wyłączone";
        private String socialSpyEnabled = "<green>Włączony";
        private String socialSpyDisabled = "<red>Wyłączony";
    }

    public PLPlaceholders placeholders() {
        return this.placeholders;
    }
}
