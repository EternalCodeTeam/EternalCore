package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.language.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class ENHelpOpSection implements Messages.HelpOpSection {

    public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
    public String send = "&8» &aThis message has been successfully sent to administration";
    public String coolDown = "&8» &cYou can use this command for: &6{TIME}";


}
