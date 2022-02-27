package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.Messages;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLAdminChatSection implements Messages.AdminChatSection {

    public String format = "&8[&4Administracja&8] &c{NICK}&8: &f{TEXT}";

}
