package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLTeleportSection implements MessagesConfiguration.TeleportSection {

    public String actionBarMessage = "&aTeleportacja za &f{TIME}";
    public String cancel = "&4Blad: &cRuszyłeś się, teleportacja przerwana!";
    public String teleported = "&8» &aPrzeteleportowano!";
    public String teleporting = "&8» &aTeleportuje...";
    public String haveTeleport = "&4Blad: &cTeleportujesz się już!";

}
