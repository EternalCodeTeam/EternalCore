package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.Language;

import java.io.File;

public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {

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
    public Language getLanguage() {
        return Language.PL;
    }

    @Override
    public ArgumentSection argument() {
        return argumentSection;
    }

    @Override
    public HelpopSection helpOp() {
        return helpOpSection;
    }

    @Override
    public AdminChatSection adminChat() {
        return adminChatSection;
    }

    @Override
    public ChatSection chat() {
        return chatSection;
    }

    @Override
    public TeleportSection teleport() {
        return teleportSection;
    }

    @Override
    public OtherMessages other() {
        return otherMessages;
    }

}
