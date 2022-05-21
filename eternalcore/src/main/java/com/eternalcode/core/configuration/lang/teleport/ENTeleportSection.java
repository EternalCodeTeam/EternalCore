package com.eternalcode.core.configuration.lang.teleport;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENTeleportSection implements Messages.TeleportSection {
    public String actionBarMessage = "&aTeleporting in &f{TIME}";
    public String cancel = "&8» &cYou've moved, teleportation canceled!";
    public String teleported = "&8» &aTeleported!";
    public String teleporting = "&8» &aTeleporting...";
    public String haveTeleport = "&8» &cYou are in teleport!";

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
