package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLHelpopSection implements Messages.HelpopSection {

    public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
    public String send = "&8» &aWiadomość została wysłana do administracji";
    public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";

}
