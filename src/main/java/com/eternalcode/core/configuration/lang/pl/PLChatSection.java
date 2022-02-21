package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLChatSection implements MessagesConfiguration.ChatSection {

    public String disabled = "&8» &cChat has been disabled by {NICK}!";
    public String enabled = "&8» &aThe chat has been enabled by {NICK}!";
    public String cleared = "&8» &6Chat has been cleared by {NICK}!";
    public String alreadyDisabled = "&8» &aChat already off!";
    public String alreadyEnabled = "&8» &aChat already on!";
    public String slowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
    public String slowMode = "&8» &cYou can write the next message for: &6{TIME}";
    public String disable = "&8» &cChat is currently disabled!";
    public String noCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";

}
