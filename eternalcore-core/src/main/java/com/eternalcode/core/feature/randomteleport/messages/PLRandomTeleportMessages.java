package com.eternalcode.core.feature.randomteleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLRandomTeleportMessages extends OkaeriConfig implements RandomTeleportMessages {
    @Comment(" ")
    Notice randomTeleportStarted = Notice.builder()
        .chat("<color:#9d6eef>► <white>Rozpoczynanie procesu losowania lokalizacji...")
        .title("<color:#9d6eef>Losowy teleport")
        .subtitle("<white>Wyszukiwanie lokalizacji, proszę czekać...")
        .build();

    Notice randomTeleportFailed =
        Notice.chat("<red>✘ <dark_red>Nie udało się znaleźć bezpiecznej lokalizacji, spróbuj ponownie!");

    Notice teleportedToRandomLocation =
        Notice.chat("<color:#9d6eef>► <white>Zostałeś przeteleportowany na losową lokalizację!");

    @Comment({
        "# {PLAYER} - Gracz który został teleportowany, {WORLD} - Świat, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat(
        "<color:#9d6eef>► <white>Przeteleportowałeś gracza <color:#9d6eef>{PLAYER} <white>na losową lokalizację! Jego aktualna lokalizacja to: świat: {WORLD} x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}.");

    @Comment({" ", "# {TIME} - Czas do następnego użycia komendy (cooldown)"})
    Notice randomTeleportDelay =
        Notice.chat("<red>✘ <dark_red>Możesz skorzystać z losowej teleportacji dopiero za <dark_red>{TIME}!");
}
