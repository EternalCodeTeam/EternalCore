package com.eternalcode.core.configuration.lang.helpop;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLHelpOpSection implements Messages.HelpOpSection {
    public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
    public String send = "&8» &aWiadomość została wysłana do administracji";
    public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";

    public String format() {
        return this.format;
    }

    public String send() {
        return this.send;
    }

    public String coolDown() {
        return this.coolDown;
    }
}
