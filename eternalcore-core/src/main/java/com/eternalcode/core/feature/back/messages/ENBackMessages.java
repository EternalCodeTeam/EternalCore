package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENBackMessages extends OkaeriConfig implements BackMessages {

    public Notice lastLocationNotFound = Notice.chat(
        "<red>► <white>You don't have any last location to teleport to!");

    public Notice teleportedToLastTeleportLocation = Notice.chat(
        "<green>► <white>You have been teleported to your last location!");
    public Notice teleportedTargetPlayerToLastTeleportLocation = Notice.chat(
        "<green>► <white>Player <green>{PLAYER} <white>has been teleported to their last location!");
    public Notice teleportedToLastTeleportLocationByAdmin = Notice.chat(
        "<green>► <white>You have been teleported to your last location by an administrator!");

    public Notice teleportedToLastDeathLocation = Notice.chat(
        "<green>► <white>You have been teleported to your last death location!");
    public Notice teleportedTargetPlayerToLastDeathLocation = Notice.chat(
        "<green>► <white>Player <green>{PLAYER} <white>has been teleported to their last death location!");
    public Notice teleportedToLastDeathLocationByAdmin = Notice.chat(
        "<green>► <white>You have been teleported to your last death location by an administrator!");
}
