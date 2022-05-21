package com.eternalcode.core.configuration.lang.warp;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENWarpSection implements Messages.WarpSection {

    public String availableList = "&8» List available warps: {WARPS}";
    public String notExist = "&8» &cThis warp doesn't exist";
    public String noPermission = "&8» &cYou don't have permission to this warp!";
    public String disabled = "&8» &cThis warp is currently disabled!";

    public String availableList() {
        return this.availableList;
    }

    public String notExist() {
        return this.notExist;
    }

    public String noPermission() {
        return this.noPermission;
    }

    public String disabled() {
        return this.disabled;
    }
}
