package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;

import java.io.File;

@Getter
public class PLMessagesConfiguration extends AbstractConfigWithResource implements MessagesConfiguration {

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public PLArgumentSection argumentSection = new PLArgumentSection();
    public PLHelpopSection helpOpSection = new PLHelpopSection();
    public PLAdminChatSection adminChatSection = new PLAdminChatSection();
    public PLTeleportSection teleportSection = new PLTeleportSection();
    public PLChatSection chatSection = new PLChatSection();
    public PLOtherMessages otherMessages = new PLOtherMessages();

    @Override
    public String getLang() {
        return "pl";
    }

}
