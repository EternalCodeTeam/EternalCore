package com.eternalcode.core.feature.randomteleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
@Getter
@Accessors(fluent = true)
public class ENRandomTeleportMessages extends OkaeriConfig implements RandomTeleportMessages {
    Notice randomTeleportStarted = Notice.builder()
        .chat("<color:#9d6eef>► <white>Teleportation to a random location has started!")
        .title("<color:#9d6eef>Random teleport")
        .subtitle("<white>Searching, please wait...")
        .build();

    Notice randomTeleportFailed =
        Notice.chat("<red>✘ <dark_red>A safe location could not be found, please try again!");

    @Comment({
        "# {PLAYER} - Player who has been teleported, {WORLD} - World name, {X} - X coordinate, {Y} - Y coordinate, {Z} - Z coordinate"})
    Notice teleportedToRandomLocation =
        Notice.chat("<color:#9d6eef>► <white>You have been teleported to a random location!");

    @Comment(" ")
    Notice teleportedSpecifiedPlayerToRandomLocation = Notice.chat(
        "<color:#9d6eef>► <white>You have teleported <color:#9d6eef>{PLAYER} <white>to a random location! His current location is: world: {WORLD}, x: <color:#9d6eef>{X}<white>, y: <color:#9d6eef>{Y}<white>, z: <color:#9d6eef>{Z}.");

    @Comment({" ", "# {TIME} - Time to next use (cooldown)"})
    Notice randomTeleportDelay = Notice.chat("<red>✘ <dark_red>You can use random teleport after <red>{TIME}!");
}
