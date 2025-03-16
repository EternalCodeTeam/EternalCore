package com.eternalcode.core.feature.teleportrequest.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENTeleportRequestMessages implements TeleportRequestMessages {
    public Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>You can't teleport to yourself!");
    public Notice tpaAlreadySentMessage =
        Notice.chat("<red>✘ <dark_red>You have already sent a teleportation request!");
    public Notice tpaSentMessage =
        Notice.chat("<green>► <white>You have sent a request for teleportation to a player: <green>{PLAYER}<white>!");

    public Notice tpaHereSentMessage = Notice.chat("<green>► <white>You have sent a request for teleportation to you for a player: <green>{PLAYER}<white>!");
    public Notice tpaHereReceivedMessage = Notice.builder()
        .chat("<green>► <white>You have received a request for teleportation TO a player: <gray>{PLAYER}<green>!")
        .chat(
            "<hover:show_text:'<green>Teleport to the player?</green>'><gold><click:suggest_command:'/tpahereaccept {PLAYER}'><dark_gray>» <gold>/tpahereaccept {PLAYER} <green>to accept! <gray>(Click)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpaheredeny {PLAYER}'><dark_gray>» <gold>/tpaheredeny {PLAYER} <red><green>to deny! <gray>(Click)</gray></click></gold></hover>")
        .build();

    @Description({
        " ",
        "# We used MiniMessages formatting in these messages",
        "# The current request acceptance message allows the player to click on it to accept the teleport request with MiniMessages!",
        "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
    })
    @Description({" ", "# {PLAYER} - Player who sent the request to another player"})
    public Notice tpaReceivedMessage = Notice.builder()
        .chat("<green>► <white>You have received a request for teleportation from a player: <gray>{PLAYER}<green>!")
        .chat(
            "<hover:show_text:'<green>Accept request for teleports?</green>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <green>to accept! <gray>(Click)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red><green>to deny! <gray>(Click)</gray></click></gold></hover>")
        .build();

    @Description(" ")
    public Notice tpaDenyNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>You do not have a teleport request from this player!");

    @Description({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
    public Notice tpaDenyDoneMessage =
        Notice.chat("<red>✘ <dark_red>You have declined a teleport request from a player: <red>{PLAYER}<dark_red>!");

    @Description({" ", "# {PLAYER} - Player who declines the tpa request"})
    public Notice tpaDenyReceivedMessage =
        Notice.chat("<red>✘ <dark_red>Player: <red>{PLAYER} <dark_red>rejected your teleport request!");

    @Description(" ")
    public Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>All players have denied your teleport request!");

    @Description({" ", "# {PLAYER} - Player who sent tpa request to another player"})
    public Notice tpaAcceptMessage =
        Notice.chat("<green>► <white>You have accepted the teleportation from the player: <green>{PLAYER}<white>!");

    @Description(" ")
    public Notice tpaAcceptNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>This player has not sent you a teleport request!");

    @Description({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
    public Notice tpaAcceptReceivedMessage =
        Notice.chat("<green>► <white>Player: <green>{PLAYER} <white>accepted your teleportation request!");

    @Description(" ")
    public Notice tpaAcceptAllAccepted =
        Notice.chat("<green>► <white>All players have accepted your teleport request!");

    @Description(" ")
    public Notice tpaTargetIgnoresYou = Notice.chat("<green>► <red>{PLAYER} <white>is ignoring you!");
}
