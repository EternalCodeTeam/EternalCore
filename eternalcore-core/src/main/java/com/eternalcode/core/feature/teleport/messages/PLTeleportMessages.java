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
        Notice.chat("<green>► <white>Przeteleportowano do gracza <green>{PLAYER}<white>!");

    @Comment({
        "# {PLAYER} - Gracz który został teleportowany, {ARG-PLAYER} - Gracz do którego został teleportowany inny gracz"})
    Notice teleportedPlayerToPlayer = Notice.chat(
        "<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do gracza <green>{ARG-PLAYER}<white>!");

    @Comment({"# {Y} - Koordynat Y najwyżej położonego bloku"})
    Notice teleportedToHighestBlock =
        Notice.chat("<green>► <white>Pomyślnie przeteleportowano do najwyższego bloku! (Y: {Y})");

    @Comment(" ")
    Notice teleportedAllToPlayer =
        Notice.chat("<green>► <white>Przeteleportowano wszystkich graczy do ciebie!");

    // Task
    @Comment({"# {TIME} - Czas teleportacji"})
    Notice teleportTimerFormat = Notice.actionbar("<green>► <white>Teleportacja za <green>{TIME}");
    @Comment(" ")
    Notice teleported = Notice.builder()
        .chat("<green>► <white>Przeteleportowano!")
        .actionBar("<green>► <white>Przeteleportowano!")
        .build();

    Notice teleporting = Notice.chat("<green>► <white>Teleportowanie...");
    Notice teleportTaskCanceled =
        Notice.chat("<red>✘ <dark_red>Ruszyłeś się, teleportacja została przerwana!");
    Notice teleportTaskAlreadyExist = Notice.chat("<red>✘ <dark_red>Teleportujesz się już!");

    // Coordinates XYZ
    @Comment({" ", "# {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    Notice teleportedToCoordinates = Notice.chat(
        "<green>► <white>Przeteleportowano na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");
    @Comment({" ",
              "# {PLAYER} - Gracz który został teleportowany, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    Notice teleportedSpecifiedPlayerToCoordinates = Notice.chat(
        "<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>na współrzędne x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}");

    // Back
    @Comment(" ")
    Notice teleportedToLastLocation =
        Notice.chat("<green>► <white>Przeteleportowano do ostatniej lokalizacji!");
    @Comment({" ", "# {PLAYER} - Gracz który został teleportowany"})
    Notice teleportedSpecifiedPlayerLastLocation =
        Notice.chat("<green>► <white>Przeteleportowano gracza <green>{PLAYER} <white>do ostatniej lokalizacji!");
    @Comment(" ")
    Notice lastLocationNoExist = Notice.chat("<red>✘ <dark_red>Nie ma zapisanej ostatniej lokalizacji!");
}
