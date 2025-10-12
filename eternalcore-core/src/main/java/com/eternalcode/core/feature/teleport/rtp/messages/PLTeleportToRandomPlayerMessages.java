package com.eternalcode.core.feature.teleport.rtp.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTeleportToRandomPlayerMessages extends OkaeriConfig implements TeleportToRandomPlayerMessages {

    public Notice randomPlayerNotFound =
        Notice.chat("<red>✘ <dark_red>Nie można odnaleźć gracza do teleportacji!");

    @Comment("{PLAYER} - Nazwa gracza, do którego zostałeś teleportowany")
    public Notice teleportedToRandomPlayer =
        Notice.chat("<green>► <white>Zostałeś losowo teleportowany do <green>{PLAYER}<white>!");

    public Notice randomPlayerInRangeNotFound =
        Notice.chat("<red>✘ <dark_red>Nie można odnaleźć gracza w zasięgu do teleportacji!");

    @Comment("{PLAYER} - Nazwa gracza, do którego zostałeś teleportowany w zasięgu")
    public Notice teleportedToRandomPlayerInRange =
        Notice.chat("<green>► <white>Zostałeś losowo teleportowany do gracza w zasięgu: <green>{PLAYER}<white>!");
}
