package com.eternalcode.core.configuration.lang.format;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLFormatSection implements Messages.Format {

    public String enable = "&awlaczone";
    public String disable = "&cwylaczone";

    public String formatEnable() {
        return this.enable;
    }

    public String formatDisable() {
        return this.disable;
    }
}
