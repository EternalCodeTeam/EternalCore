package com.eternalcode.core.feature.deathteleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENDeathTeleportMessages extends OkaeriConfig implements DeathTeleportMessages {

    @Comment("# {TIME} - Time left before the teleportation")
    public Notice deathTeleportStarted = Notice.actionbar(
        "<color:#9d6eef>► <white>Teleporting you back to your death location in <color:#9d6eef>{TIME}<white>...");

    public Notice deathTeleportSuccess = Notice.chat(
        "<green>► <white>You have been teleported back to your death location!");

    public Notice deathTeleportSelfEnabled = Notice.chat(
        "<green>► <white>Automatic teleportation to your death location is now <green>enabled<white>!");
    public Notice deathTeleportSelfDisabled = Notice.chat(
        "<red>► <white>Automatic teleportation to your death location is now <red>disabled<white>!");

    public Notice deathTeleportOtherEnabled = Notice.chat(
        "<green>► <white>Automatic teleportation to death location has been <green>enabled <white>for <green>{PLAYER}<white>!");
    public Notice deathTeleportOtherDisabled = Notice.chat(
        "<red>► <white>Automatic teleportation to death location has been <red>disabled <white>for <green>{PLAYER}<white>!");
}
