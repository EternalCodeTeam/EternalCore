package com.eternalcode.core.configuration.lang.chat;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENChatSection implements Messages.ChatSection {
    public String disabled = "&8» &cChat has been disabled by {NICK}!";
    public String enabled = "&8» &aThe chat has been enabled by {NICK}!";
    public String cleared = "&8» &6Chat has been cleared by {NICK}!";
    public String alreadyDisabled = "&8» &aChat already off!";
    public String alreadyEnabled = "&8» &aChat already on!";
    public String slowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
    public String slowMode = "&8» &cYou can write the next message for: &6{TIME}";
    public String disabledChatInfo = "&8» &cChat is currently disabled!";
    public String noCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";

    public String disabled() {
        return this.disabled;
    }

    public String enabled() {
        return this.enabled;
    }

    public String cleared() {
        return this.cleared;
    }

    public String alreadyDisabled() {
        return this.alreadyDisabled;
    }

    public String alreadyEnabled() {
        return this.alreadyEnabled;
    }

    public String slowModeSet() {
        return this.slowModeSet;
    }

    public String slowMode() {
        return this.slowMode;
    }

    public String disabledChatInfo() {
        return this.disabledChatInfo;
    }

    public String noCommand() {
        return this.noCommand;
    }
}
