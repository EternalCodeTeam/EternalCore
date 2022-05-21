package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.configuration.lang.adminchat.PLAdminChatSection;
import com.eternalcode.core.configuration.lang.argument.PLArgumentSection;
import com.eternalcode.core.configuration.lang.chat.PLChatSection;
import com.eternalcode.core.configuration.lang.format.PLFormatSection;
import com.eternalcode.core.configuration.lang.helpop.PLHelpOpSection;
import com.eternalcode.core.configuration.lang.other.PLOtherMessages;
import com.eternalcode.core.configuration.lang.privatemessage.PLPrivateMessage;
import com.eternalcode.core.configuration.lang.teleport.PLTeleportSection;
import com.eternalcode.core.configuration.lang.tpa.PLTpaSection;
import com.eternalcode.core.configuration.lang.warp.PLWarpSection;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;

import java.io.File;

public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {
    private final Language language = Language.PL;

    public PLArgumentSection argument = new PLArgumentSection();
    public PLFormatSection format = new PLFormatSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLWarpSection warp = new PLWarpSection();
    public PLPrivateMessage privateMessage = new PLPrivateMessage();
    public PLTpaSection tpa = new PLTpaSection();
    public PLOtherMessages other = new PLOtherMessages();

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Language getLanguage() {
        return this.language;
    }

    public PLArgumentSection argument() {
        return this.argument;
    }

    public PLFormatSection format() {
        return this.format;
    }

    public PLHelpOpSection helpOp() {
        return this.helpOp;
    }

    public PLAdminChatSection adminChat() {
        return this.adminChat;
    }

    public PLTeleportSection teleport() {
        return this.teleport;
    }

    public PLChatSection chat() {
        return this.chat;
    }

    public PLTpaSection tpa() {
        return this.tpa;
    }

    public PLWarpSection warp() {
        return this.warp;
    }

    public PrivateMessage privateMessage() {
        return this.privateMessage;
    }

    public PLOtherMessages other() {
        return this.other;
    }

}
