package com.eternalcode.core.feature.teleportrequest.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLTeleportRequestMessages implements TeleportRequestMessages {
    public Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>Nie możesz teleportować się samodzielnie!");
    public Notice tpaAlreadySentMessage = Notice.chat("<red>✘ <dark_red>Już wysłałeś prośbę o teleportację!");
    @Description({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportację"})
    public Notice tpaSentMessage =
        Notice.chat("<green>► <white>Wysłałeś prośbę o teleportację do gracza: <green>{PLAYER}<white>!");

    public Notice tpaHereSentMessage = Notice.chat("<green>► <white>Wysłałeś prośbę o teleportację do ciebie dla gracza: <green>{PLAYER}<white>!");
    public Notice tpaHereReceivedMessage = Notice.builder()
        .chat("<green>► <white>Otrzymałeś prośbę o teleportację do gracza: <green>{PLAYER}<white>!")
        .chat(
            "<hover:show_text:'<green>Teleportować się do gracza?</green>'><gold><click:suggest_command:'/tpahereaccept {PLAYER}'><dark_gray>» <gold>/tpahereaccept {PLAYER} <green>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpaheredeny {PLAYER}'><dark_gray>» <gold>/tpaheredeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        .build();
    @Description({
        " ",
        "# W tych wiadomościach użyliśmy formatowania MiniMessages",
        "# Obecna wiadomość od akceptacji prośby umożliwia graczowi kliknięcie w nią, aby zaakceptować prośbę o teleportację dzięki MiniMessages!",
        "# Więcej informacji znajdziesz na stronie: https://docs.adventure.kyori.net/minimessage/format.html",
    })
    @Description({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    public Notice tpaReceivedMessage = Notice.builder()
        .chat("<green>► <white>Otrzymałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!")
        .chat(
            "<hover:show_text:'<green>Akceptować prośbę o teleportacje?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>by ją zaakceptować! <gray>(Kliknij)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Odrzucić prośbę o teleportacje?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red>by ją odrzucić! <gray>(Kliknij)</gray></click></gold></hover>")
        .build();

    @Description(" ")
    public Notice tpaDenyNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>Nie otrzymałeś prośby o teleportację od tego gracza!");

    @Description({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    public Notice tpaDenyDoneMessage =
        Notice.chat("<red>✘ <dark_red>Odrzuciłeś prośbę o teleportację od gracza: <red>{PLAYER}<dark_red>!");

    @Description({" ", "# {PLAYER} - Gracz który odrzucił prośbę o teleportacje"})
    public Notice tpaDenyReceivedMessage =
        Notice.chat("<red>► <dark_red>Gracz: <red>{PLAYER} <dark_red>odrzucił twoją prośbę o teleportację!");

    @Description(" ")
    public Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>Odrzucono wszystkie prośby o teleportację!");

    @Description({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    public Notice tpaAcceptMessage =
        Notice.chat("<green>► <white>Zaakceptowałeś prośbę o teleportację od gracza: <green>{PLAYER}<white>!");

    @Description(" ")
    public Notice tpaAcceptNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>Ten gracz nie wysłał do ciebie prośby o teleportację!");

    @Description({" ", "# {PLAYER} - Gracz który wysłał prośbę o teleportacje do innego gracza"})
    public Notice tpaAcceptReceivedMessage =
        Notice.chat("<green>► <white>Gracz: <green>{PLAYER} <white>zaakceptował twoją prośbę o teleportację!");

    @Description(" ")
    public Notice tpaAcceptAllAccepted = Notice.chat("<green>► <white>Zaakceptowano wszystkie prośby o teleportację!");

    @Description(" ")
    public Notice tpaTargetIgnoresYou = Notice.chat("<green>► <red>{PLAYER} <white>ignoruje Cię!");
}
