package com.eternalcode.core.feature.randomteleport.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLRandomTeleportMessages implements RandomTeleportMessages {
    @Description(" ")
    public Notice randomTeleportStarted = Notice.builder()
        .chat("<green>► <white>Rozpoczynanie procesu losowania lokalizacji...")
        .title("<green>Losowy teleport")
        .subtitle("<white>Wyszukiwanie lokalizacji, proszę czekać...")
        .build();

    public Notice randomTeleportFailed =
        Notice.chat("<red>✘ <dark_red>Nie udało się znaleźć bezpiecznej lokalizacji, spróbuj ponownie!");

    public Notice teleportedToRandomLocation =
        Notice.chat("<green>► <white>Zostałeś przeteleportowany na losową lokalizację!");

    @Description({
        "# {PLAYER} - Gracz który został teleportowany, {WORLD} - Świat, {X} - Koordynat X, {Y} - Koordynat Y, {Z} - Koordynat Z"})
    public Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat(
        "<green>► <white>Przeteleportowałeś gracza <green>{PLAYER} <white>na losową lokalizację! Jego aktualna lokalizacja to: świat: {WORLD} x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}.");

    @Description({" ", "# {TIME} - Czas do następnego użycia komendy (cooldown)"})
    public Notice randomTeleportDelay =
        Notice.chat("<red>✘ <dark_red>Możesz skorzystać z losowej teleportacji dopiero za <dark_red>{TIME}!");
}
