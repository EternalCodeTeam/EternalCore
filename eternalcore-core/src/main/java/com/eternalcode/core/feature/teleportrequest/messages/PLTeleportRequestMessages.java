package com.eternalcode.core.feature.teleportrequest.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTeleportRequestMessages extends OkaeriConfig implements TeleportRequestMessages {
    Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>Nie możesz teleportować się samodzielnie!");
    Notice tpaAlreadySentMessage = Notice.chat("<red>✘ <dark_red>Już wysłałeś prośbę o teleportację!");
    @Comment({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportację"})
    Notice tpaSentMessage =
        Notice.chat("<color:#9d6eef>► <white>Wysłałeś prośbę o teleportację do gracza: <color:#9d6eef>{PLAYER}<white>!");

    Notice tpaHereSent = Notice.chat("<color:#9d6eef>► <white>Wysłałeś prośbę o teleportację gracza <color:#9d6eef>{PLAYER}<white> do twojej lokalizacji!");
    Notice tpaHereSentToAll = Notice.chat("<color:#9d6eef>► <white>Wysłano prośbę o teleportację do wszystkich graczy!");
    Notice tpaHereReceived = Notice.builder()
        .chat("<color:#9d6eef>► <white>Otrzymałeś prośbę o teleportację do gracza: <color:#9d6eef>{PLAYER}<white>!")
        .chat(
            "<hover:show_text:'<color:#9d6eef>Teleportować się do gracza?</color:#9d6eef>'><gold><click:suggest_command:'/tpahereaccept {PLAYER}'><dark_gray>» <gold>/tpahereaccept {PLAYER} <color:#9d6eef>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpaheredeny {PLAYER}'><dark_gray>» <gold>/tpaheredeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        .build();

    @Comment({
        " ",
        "# W tych wiadomościach użyliśmy formatowania MiniMessages",
        "# Obecna wiadomość od akceptacji prośby umożliwia graczowi kliknięcie w nią, aby zaakceptować prośbę o teleportację dzięki MiniMessages!",
        "# Więcej informacji znajdziesz na stronie: https://docs.adventure.kyori.net/minimessage/format.html",
    })
    @Comment({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    Notice tpaReceivedMessage = Notice.builder()
        .chat("<color:#9d6eef>► <white>Otrzymałeś prośbę o teleportację od gracza: <color:#9d6eef>{PLAYER}<white>!")
        .chat(
            "<hover:show_text:'<color:#9d6eef>Akceptować prośbę o teleportacje?</color:#9d6eef>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <color:#9d6eef>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        .build();

    @Comment(" ")
    Notice tpaDenyNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>Nie otrzymałeś prośby o teleportację od tego gracza!");

    @Comment({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    Notice tpaDenyDoneMessage =
        Notice.chat("<red>✘ <dark_red>Odrzuciłeś prośbę o teleportację od gracza: <red>{PLAYER}<dark_red>!");

    @Comment({" ", "# {PLAYER} - Gracz który odrzucił prośbę o teleportacje"})
    Notice tpaDenyReceivedMessage =
        Notice.chat("<red>► <dark_red>Gracz: <red>{PLAYER} <dark_red>odrzucił twoją prośbę o teleportację!");

    @Comment(" ")
    Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>Odrzucono wszystkie prośby o teleportację!");

    @Comment({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    Notice tpaAcceptMessage =
        Notice.chat("<color:#9d6eef>► <white>Zaakceptowałeś prośbę o teleportację od gracza: <color:#9d6eef>{PLAYER}<white>!");

    @Comment(" ")
    Notice tpaAcceptNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>Ten gracz nie wysłał do ciebie prośby o teleportację!");

    @Comment({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    Notice tpaAcceptReceivedMessage =
        Notice.chat("<color:#9d6eef>► <white>Gracz: <color:#9d6eef>{PLAYER} <white>zaakceptował twoją prośbę o teleportację!");

    @Comment(" ")
    Notice tpaAcceptAllAccepted = Notice.chat("<color:#9d6eef>► <white>Zaakceptowano wszystkie prośby o teleportację!");

    @Comment(" ")
    Notice tpaTargetIgnoresYou = Notice.chat("<color:#9d6eef>► <red>{PLAYER} <white>ignoruje Cię!");
}
