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
        "<green>► <white>Zostałeś przeteleportowany do ostatniej lokalizacji!");
    public Notice teleportedTargetToLastTeleportLocation = Notice.chat(
        "<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany do swojej ostatniej lokalizacji!");

    public Notice teleportedToLastDeathLocation = Notice.chat(
        "<green>► <white>Zostałeś przeteleportowany do ostatniej lokalizacji śmierci!");

    public Notice teleportedTargetToLastDeathLocation = Notice.chat(
        "<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany do swojej ostatniej lokalizacji śmierci!");
}
