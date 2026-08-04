package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLBackMessages extends OkaeriConfig implements BackMessages {

    public Notice lastLocationNotFound = Notice.chat(
        "<red>► <white>Nie masz żadnej ostatniej lokalizacji, do której można się teleportować!");

    public Notice teleportedToLastTeleportLocation = Notice.chat(
        "<green>► <white>Teleportowanie do ostatniej lokalizacji!");
    public Notice teleportedTargetPlayerToLastTeleportLocation = Notice.chat(
        "<green>► <white>Teleportowanie gracza <green>{PLAYER} <white>do jego ostatniej lokalizacji!");
    public Notice teleportedToLastTeleportLocationByAdmin = Notice.chat(
        "<green>► <white>Administrator teleportuje Cię do ostatniej lokalizacji!");

    public Notice teleportedToLastDeathLocation = Notice.chat(
        "<green>► <white>Teleportowanie do ostatniej lokalizacji śmierci!");
    public Notice teleportedTargetPlayerToLastDeathLocation = Notice.chat(
        "<green>► <white>Teleportowanie gracza <green>{PLAYER} <white>do jego ostatniej lokalizacji śmierci!");
    public Notice teleportedToLastDeathLocationByAdmin = Notice.chat(
        "<green>► <white>Administrator teleportuje Cię do ostatniej lokalizacji śmierci!");

}
