package com.eternalcode.core.configuration.lang.adminchat;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENAdminChatSection implements Messages.AdminChatSection {
    public String format = "&8[&4AdminChat&8] &c{NICK}&8: &f{TEXT}";

    public String format() {
        return this.format;
    }
}
