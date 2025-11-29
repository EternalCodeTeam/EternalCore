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
            "<red>✘ <dark_red>Nie możesz nikomu odpowiadać, ponieważ nie otrzymałeś żadnej wiadomości prywatnej!");

    @Comment("# {TARGET} - Gracz do którego chcesz wysłać wiadomość, {MESSAGE} - Treść wiadomości")
    Notice msgYouToTarget = Notice.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment({ " ", "# {SENDER} - Gracz który wysłał wiadomość, {MESSAGE} - Treść wiadomości" })
    Notice msgTargetToYou = Notice.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment("# {SENDER} - Gracz który wysłał wiadomość, {TARGET} - Gracz do którego wysłał wiadomość, {MESSAGE} - Treść wiadomości")
    Notice socialSpyMessage = Notice.chat(
            "<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");

    @Comment(" ")
    Notice socialSpyEnable = Notice.chat("<color:#9d6eef>► <white>SocialSpy został {STATE}<white>!");
    Notice socialSpyDisable = Notice.chat("<red>► <white>SocialSpy został {STATE}<white>!");

    Notice receiverDisabledMessages = Notice.chat("<red>✘ <dark_red>Wiadomości prywatne zostały wyłączone!");

    Notice selfMessagesDisabled = Notice.chat("<color:#9d6eef>► <white>Wiadomości prywatne zostały <red>wyłączone!");
    Notice selfMessagesEnabled = Notice
            .chat("<color:#9d6eef>► <white>Wiadomości prywatne zostały <color:#9d6eef>włączone!");

    @Comment({ " ", "# {PLAYER} - Gracz któremu wyłączono wiadomości prywatne" })
    Notice otherMessagesDisabled = Notice.chat(
            "<color:#9d6eef>► <white>Wiadomości prywatne zostały <red>wyłączone <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");
    Notice otherMessagesEnabled = Notice.chat(
            "<color:#9d6eef>► <white>Wiadomości prywatne zostały <color:#9d6eef>włączone <white>dla gracza <color:#9d6eef>{PLAYER}<white>!");

}
