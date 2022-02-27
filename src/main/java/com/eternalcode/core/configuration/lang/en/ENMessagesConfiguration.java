package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import com.eternalcode.core.language.Language;
import lombok.Getter;

import java.io.File;

@Getter
public class ENMessagesConfiguration extends AbstractConfigWithResource implements MessagesConfiguration {

    public ENMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public ENArgumentSection argumentSection = new ENArgumentSection();
    public ENHelpopSection helpOpSection = new ENHelpopSection();
    public ENAdminChatSection adminChatSection = new ENAdminChatSection();
    public ENTeleportSection teleportSection = new ENTeleportSection();
    public ENChatSection chatSection = new ENChatSection();
    public ENOtherMessages otherMessages = new ENOtherMessages();

    @Override
    public Language getLanguage() {
        return Language.EN;
    }
}
