package com.eternalcode.core.feature.back.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLBackMessages extends OkaeriConfig implements BackMessages {

    public Notice lastLocationNoExist = Notice.chat("<red>► <white>Nie masz żadnej ostatniej lokalizacji, do której można się teleportować!");
    public Notice teleportedToLastLocation = Notice.chat("<green>► <white>Zostałeś przeteleportowany do ostatniej lokalizacji!");
    public Notice teleportedSpecifiedPlayerLastLocation = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>został przeteleportowany do swojej ostatniej lokalizacji!");

}
