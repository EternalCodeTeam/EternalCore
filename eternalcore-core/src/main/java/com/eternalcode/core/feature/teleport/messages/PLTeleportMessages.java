package com.eternalcode.core.feature.teleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTeleportMessages extends OkaeriConfig implements TeleportMessages {
    // teleport
    @Comment({"# {PLAYER} - Gracz który został teleportowany"})
    Notice teleportedToPlayer =
        Notice.chat("<color:#9d6eef>► <white>Przeteleportowano do gracza <color:#9d6eef>{PLAYER}<white>!");

    @Comment({
        "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz"})
    Notice teleportedPlayerToPlayer = Notice.chat(
        "<color:#9d6eef>► <white>Przeteleportowano gracza <color:#9d6eef>{PLAYER} <white>do gracza <color:#9d6eef>{ARG-PLAYER}<white>!");

    @Comment({"# {Y} - Koordynat Y najwyżej położonego bloku"})
    Notice teleportedToHighestBlock =
        Notice.chat("<color:#9d6eef>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

    @Comment(" ")
    Notice teleportedAllToPlayer =
        Notice.chat("<color:#9d6eef>► <white>Przeteleportowano wszystkich graczy do ciebie!");

    // Task
    @Comment({"# {TIME} - Czas teleportacji"})
    Notice teleportTimerFormat = Notice.actionbar("<color:#9d6eef>► <white>Teleportacja za <color:#9d6eef>{TIME}");
    @Comment(" ")
    Notice teleported = Notice.builder()
        .chat("<color:#9d6eef>► <white>Przeteleportowano!")
        .actionBar("<color:#9d6eef>► <white>Przeteleportowano!")
        .build();

    Notice teleporting = Notice.chat("<color:#9d6eef>► <white>Teleportowanie...");
    Notice teleportTaskCanceled =
        Notice.chat("<red>✘ <dark_red>Ruszyłeś się, teleportacja została przerwana!");
    Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>Teleportujesz się już!");

    // Coordinates XYZ
    @Comment({" ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    Notice teleportedToCoordinates = Notice.chat(
        "<color:#9d6eef>► <white>Przeteleportowano na współrzędne x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}");
    @Comment({" ",
              "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat(
        "<color:#9d6eef>► <white>Przeteleportowano gracza <color:#9d6eef>{PLAYER} <white>na współrzędne x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}");
}
