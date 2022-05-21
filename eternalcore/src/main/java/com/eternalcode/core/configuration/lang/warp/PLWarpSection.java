package com.eternalcode.core.configuration.lang.warp;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLWarpSection implements Messages.WarpSection {
    public String availableList = "&8» Lista warpów: {WARPS}";
    public String notExist = "&8» &cNie odnaleziono takiego warpa!";
    public String noPermission = "&8» &cNie masz uprawnien do tego warpa!";
    public String disabled = "&8» &cTen warp jest aktualnie wyłączony!";

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
