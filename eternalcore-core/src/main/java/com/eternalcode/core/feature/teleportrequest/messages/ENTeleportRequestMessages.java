package com.eternalcode.core.feature.teleportrequest.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTeleportRequestMessages extends OkaeriConfig implements TeleportRequestMessages {
    Notice tpaSelfMessage = Notice.chat("<red>✘ <dark_red>You can't teleport to yourself!");
    Notice tpaAlreadySentMessage =
        Notice.chat("<red>✘ <dark_red>You have already sent a teleportation request!");
    Notice tpaSentMessage =
        Notice.chat("<color:#9d6eef>► <white>You have sent a request for player <color:#9d6eef>{PLAYER}<white> to teleport to you!");

    Notice tpaHereSent = Notice.chat("<color:#9d6eef>► <white>You have sent a request for teleportation to you for a player: <color:#9d6eef>{PLAYER}<white>!");
    Notice tpaHereSentToAll = Notice.chat("<color:#9d6eef>► <white>You have sent a request for teleportation to all players!");
    Notice tpaHereReceived = Notice.builder()
        .chat("<color:#9d6eef>► <white>You have received a request for teleportation TO a player: <gray>{PLAYER}<color:#9d6eef>!")
        .chat(
            "<hover:show_text:'<color:#9d6eef>Teleport to the player?</color:#9d6eef>'><gold><click:suggest_command:'/tpahereaccept {PLAYER}'><dark_gray>» <gold>/tpahereaccept {PLAYER} <color:#9d6eef>to accept! <gray>(Click)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpaheredeny {PLAYER}'><dark_gray>» <gold>/tpaheredeny {PLAYER} <red><color:#9d6eef>to deny! <gray>(Click)</gray></click></gold></hover>")
        .build();

    @Comment({
        " ",
        "# We used MiniMessages formatting in these messages",
        "# The current request acceptance message allows the player to click on it to accept the teleport request with MiniMessages!",
        "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
    })
    @Comment({" ", "# {PLAYER} - Player who sent the request to another player"})
    Notice tpaReceivedMessage = Notice.builder()
        .chat("<color:#9d6eef>► <white>You have received a request for teleportation from a player: <gray>{PLAYER}<color:#9d6eef>!")
        .chat(
            "<hover:show_text:'<color:#9d6eef>Accept request for teleports?</color:#9d6eef>'><gold><click:suggest_command:'/tpaccept {PLAYER}'><dark_gray>» <gold>/tpaccept {PLAYER} <color:#9d6eef>to accept! <gray>(Click)</gray></click></gold></hover>")
        .chat(
            "<hover:show_text:'<red>Decline a teleportation request?</red>'><gold><click:suggest_command:'/tpdeny {PLAYER}'><dark_gray>» <gold>/tpdeny {PLAYER} <red><color:#9d6eef>to deny! <gray>(Click)</gray></click></gold></hover>")
        .build();

    @Comment(" ")
    Notice tpaDenyNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>You do not have a teleport request from this player!");

    @Comment({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
    Notice tpaDenyDoneMessage =
        Notice.chat("<red>✘ <dark_red>You have declined a teleport request from a player: <red>{PLAYER}<dark_red>!");

    @Comment({" ", "# {PLAYER} - Player who declines the tpa request"})
    Notice tpaDenyReceivedMessage =
        Notice.chat("<red>✘ <dark_red>Player: <red>{PLAYER} <dark_red>rejected your teleport request!");

    @Comment(" ")
    Notice tpaDenyAllDenied = Notice.chat("<red>► <dark_red>All players have denied your teleport request!");

    @Comment({" ", "# {PLAYER} - Player who sent tpa request to another player"})
    Notice tpaAcceptMessage =
        Notice.chat("<color:#9d6eef>► <white>You have accepted the teleportation from the player: <color:#9d6eef>{PLAYER}<white>!");

    @Comment(" ")
    Notice tpaAcceptNoRequestMessage =
        Notice.chat("<red>✘ <dark_red>This player has not sent you a teleport request!");

    @Comment({" ", "# {PLAYER} - Player who sent a request to teleport to another player"})
    Notice tpaAcceptReceivedMessage =
        Notice.chat("<color:#9d6eef>► <white>Player: <color:#9d6eef>{PLAYER} <white>accepted your teleportation request!");

    @Comment(" ")
    Notice tpaAcceptAllAccepted =
        Notice.chat("<color:#9d6eef>► <white>All players have accepted your teleport request!");

    @Comment(" ")
    Notice tpaTargetIgnoresYou = Notice.chat("<color:#9d6eef>► <red>{PLAYER} <white>is ignoring you!");
}
