package com.eternalcode.core.configuration.lang.en;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.Language;

import java.io.File;

public class ENMessagesConfiguration extends AbstractConfigWithResource implements Messages {

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
