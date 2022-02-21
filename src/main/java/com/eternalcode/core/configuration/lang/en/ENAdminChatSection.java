package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class ENAdminChatSection implements MessagesConfiguration.AdminChatSection {

    public String format = "&8[&4AdminChat&8] &c{NICK}&8: &f{TEXT}";

}
