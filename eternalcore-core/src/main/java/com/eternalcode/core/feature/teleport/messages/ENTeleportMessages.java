package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTeleportMessages extends OkaeriConfig implements TeleportMessages {
    // teleport
    @Comment({"# {PLAYER} - Teleported players"})
    Notice teleportedToPlayer =
        Notice.chat("<green>► <white>Successfully teleported to <green>{PLAYER}<white>!");

    @Comment({"# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred"})
    Notice teleportedPlayerToPlayer =
        Notice.chat("<green>► <white>Successfully teleported <green>{PLAYER} <white>to <green>{ARG-PLAYER}<white>!");

    @Comment({"# {Y} - Y coordinate of the highest block"})
    Notice teleportedToHighestBlock =
        Notice.chat("<green>► <white>Teleported successfully to the highest block! (Y: {Y})");

    @Comment(" ")
    Notice teleportedAllToPlayer = Notice.chat("<green>► <white>All players have been teleported to you!");

    // Task
    @Comment({"# {TIME} - Teleportation time"})
    Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleporting in <green>{TIME}");
    @Comment(" ")
    Notice teleported = Notice.chat("<green>► <white>Teleported!");
    Notice teleporting = Notice.chat("<green>► <white>Teleporting...");
    Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>You've moved, teleportation canceled!");
    Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>You are in teleport!");

    // Coordinates XYZ
    @Comment({" ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    Notice teleportedToCoordinates = Notice.chat(
        "<green>► <white>Teleported to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
    @Comment({" ",
              "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat(
        "<green>► <white>Teleported <green>{PLAYER} <white>to location x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

    // Back
    @Comment(" ")
    Notice teleportedToLastLocation = Notice.chat("<green>► <white>Teleported to the last location!");
    @Comment({" ", "# {PLAYER} - Player who has been teleported"})
    Notice teleportedSpecifiedPlayerLastLocation =
        Notice.chat("<green>► <white>Teleported <green>{PLAYER} <white>to the last location!");
    @Comment(" ")
    Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Last location is not exist!");
}
