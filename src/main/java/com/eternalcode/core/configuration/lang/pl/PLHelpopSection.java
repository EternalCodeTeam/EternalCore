package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLHelpopSection implements MessagesConfiguration.HelpopSection {

    public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
    public String send = "&8» &aThis message has been successfully sent to administration";

}
