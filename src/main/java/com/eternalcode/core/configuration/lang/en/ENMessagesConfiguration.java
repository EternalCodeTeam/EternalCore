package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.Language;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;

@Getter @Accessors(fluent = true)
public class ENMessagesConfiguration extends AbstractConfigWithResource implements Messages {

    public ENMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public ENArgumentSection argument = new ENArgumentSection();
    public ENHelpopSection helpOp = new ENHelpopSection();
    public ENAdminChatSection adminChat = new ENAdminChatSection();
    public ENTeleportSection teleport = new ENTeleportSection();
    public ENChatSection chat = new ENChatSection();
    public ENOtherMessages other = new ENOtherMessages();

    @Getter @Accessors
    private static final Language language = Language.EN;

}
