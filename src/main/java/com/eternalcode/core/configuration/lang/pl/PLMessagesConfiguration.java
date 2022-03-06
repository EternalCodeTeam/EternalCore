package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.Language;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.File;

@Getter @Accessors(fluent = true)
public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public PLArgumentSection argument = new PLArgumentSection();
    public PLHelpopSection helpOp = new PLHelpopSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLOtherMessages other = new PLOtherMessages();

    @Getter @Accessors
    private static final Language language = Language.PL;

}
