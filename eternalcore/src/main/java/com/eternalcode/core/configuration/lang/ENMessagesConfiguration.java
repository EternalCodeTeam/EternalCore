package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.adminchat.ENAdminChatSection;
import com.eternalcode.core.configuration.lang.argument.ENArgumentSection;
import com.eternalcode.core.configuration.lang.chat.ENChatSection;
import com.eternalcode.core.configuration.lang.format.ENFormatSection;
import com.eternalcode.core.configuration.lang.helpop.ENHelpOpSection;
import com.eternalcode.core.configuration.lang.other.ENOtherMessages;
import com.eternalcode.core.configuration.lang.privatemessage.ENPrivateMessage;
import com.eternalcode.core.configuration.lang.teleport.ENTeleportSection;
import com.eternalcode.core.configuration.lang.tpa.ENTpaSection;
import com.eternalcode.core.configuration.lang.warp.ENWarpSection;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;

import java.io.File;

public class ENMessagesConfiguration extends AbstractConfigWithResource implements Messages {
    private final Language language = Language.EN;

    public ENArgumentSection argument = new ENArgumentSection();
    public ENFormatSection format = new ENFormatSection();
    public ENHelpOpSection helpOp = new ENHelpOpSection();
    public ENAdminChatSection adminChat = new ENAdminChatSection();
    public ENTeleportSection teleport = new ENTeleportSection();
    public ENChatSection chat = new ENChatSection();
    public ENTpaSection tpa = new ENTpaSection();
    public ENWarpSection warp = new ENWarpSection();
    public ENPrivateMessage privateMessage = new ENPrivateMessage();
    public ENOtherMessages other = new ENOtherMessages();

    public ENMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public ENArgumentSection argument() {
        return this.argument;
    }

    public ENFormatSection format() {
        return this.format;
    }

    public ENHelpOpSection helpOp() {
        return this.helpOp;
    }

    public ENAdminChatSection adminChat() {
        return this.adminChat;
    }

    public ENTeleportSection teleport() {
        return this.teleport;
    }

    public ENChatSection chat() {
        return this.chat;
    }

    public ENTpaSection tpa() {
        return this.tpa;
    }

    public ENWarpSection warp() {
        return this.warp;
    }

    public PrivateMessage privateMessage() {
        return this.privateMessage;
    }

    public Language getLanguage() {
        return this.language;
    }

    public ENOtherMessages other() {
        return this.other;
    }

}
