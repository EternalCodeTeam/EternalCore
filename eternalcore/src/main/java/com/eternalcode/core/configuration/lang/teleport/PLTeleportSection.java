package com.eternalcode.core.configuration.lang.teleport;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLTeleportSection implements Messages.TeleportSection {
    public String actionBarMessage = "&aTeleportacja za &f{TIME}";
    public String cancel = "&4Blad: &cRuszyłeś się, teleportacja przerwana!";
    public String teleported = "&8» &aPrzeteleportowano!";
    public String teleporting = "&8» &aTeleportuje...";
    public String haveTeleport = "&4Blad: &cTeleportujesz się już!";

    public String actionBarMessage() {
        return this.actionBarMessage;
    }

    public String cancel() {
        return this.cancel;
    }

    public String teleported() {
        return this.teleported;
    }

    public String teleporting() {
        return this.teleporting;
    }

    public String haveTeleport() {
        return this.haveTeleport;
    }
}
