package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.language.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class ENTeleportSection implements Messages.TeleportSection {

    public String actionBarMessage = "&aTeleporting in &f{TIME}";
    public String cancel = "&8» &cYou've moved, teleportation canceled!";
    public String teleported = "&8» &aTeleported!";
    public String teleporting = "&8» &aTeleporting...";
    public String haveTeleport = "&8» &cYou are in teleport!";

}
