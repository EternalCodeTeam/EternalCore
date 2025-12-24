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
        Notice.chat("<color:#9d6eef>► <white>Successfully teleported to <color:#9d6eef>{PLAYER}<white>!");

    @Comment({"# {PLAYER} - Teleported player, {ARG-PLAYER} - Player to whom another player has been transferred"})
    Notice teleportedPlayerToPlayer =
        Notice.chat("<color:#9d6eef>► <white>Successfully teleported <color:#9d6eef>{PLAYER} <white>to <color:#9d6eef>{ARG-PLAYER}<white>!");

    @Comment({"# {Y} - Y coordinate of the highest block"})
    Notice teleportedToHighestBlock =
        Notice.chat("<color:#9d6eef>► <white>Teleported successfully to the highest block! (Y: {Y})");

    @Comment(" ")
    Notice teleportedAllToPlayer = Notice.chat("<color:#9d6eef>► <white>All players have been teleported to you!");

    // Task
    @Comment({"# {TIME} - Teleportation time"})
    Notice teleportTimerFormat = Notice.actionbar("<color:#9d6eef>► <white>Teleporting in <color:#9d6eef>{TIME}");
    @Comment(" ")
    Notice teleported = Notice.chat("<color:#9d6eef>► <white>Teleported!");
    Notice teleporting = Notice.chat("<color:#9d6eef>► <white>Teleporting...");
    Notice teleportTaskCanceled = Notice.chat("<red>✘ <dark_red>You've moved, teleportation canceled!");
    Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>You are in teleport!");

    // Coordinates XYZ
    @Comment({" ", "# {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    Notice teleportedToCoordinates = Notice.chat(
        "<color:#9d6eef>► <white>Teleported to location x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}");
    @Comment({" ",
              "# {PLAYER} -  Player who has been teleported, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat(
        "<color:#9d6eef>► <white>Teleported <color:#9d6eef>{PLAYER} <white>to location x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}");
}
