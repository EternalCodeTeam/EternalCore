package com.eternalcode.core.feature.randomteleport.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENRandomTeleportMessages implements RandomTeleportMessages {
    public Notice randomTeleportStarted = Notice.builder()
        .chat("<green>► <white>Teleportation to a random location has started!")
        .title("<green>Random teleport")
        .subtitle("<white>Searching, please wait...")
        .build();

    public Notice randomTeleportFailed =
        Notice.chat("<red>✘ <dark_red>A safe location could not be found, please try again!");

    @Description({
        "# {PLAYER} - Player who has been teleported, {WORLD} - World name, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    public Notice teleportedToRandomLocation =
        Notice.chat("<green>► <white>You have been teleported to a random location!");

    @Description(" ")
    public Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat(
        "<green>► <white>You have teleported <green>{PLAYER} <white>to a random location! His current location is: world: {WORLD}, x: <green>{X}<white>, y: <green>{Y}<white>, z: <green>{Z}.");

    @Description({" ", "# {TIME} - Time to next use (cooldown)"})
    public Notice randomTeleportDelay = Notice.chat("<red>✘ <dark_red>You can use random teleport after <red>{TIME}!");
}
