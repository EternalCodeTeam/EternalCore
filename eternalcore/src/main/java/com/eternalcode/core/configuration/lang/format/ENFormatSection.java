package com.eternalcode.core.configuration.lang.format;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENFormatSection implements Messages.Format {
    public String enable = "&aenabled";
    public String disable = "&cdisabled";

    public String formatEnable() {
        return this.enable;
    }

    public String formatDisable() {
        return this.disable;
    }
}
