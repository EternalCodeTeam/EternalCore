package com.eternalcode.core.configuration.lang.pl;

import com.eternalcode.core.configuration.lang.MessagesConfiguration;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter @Accessors(fluent = true)
@Contextual
public class PLChatSection implements MessagesConfiguration.ChatSection {

    public String disabled = "&8» &cCzat został wyłączony przez &6{NICK}&c!";
    public String enabled = "&8» &aCzat został włączony przez &f{NICK}&a!";
    public String cleared = "&8» &6Czat zotał wyczyszczony przez &c{NICK}&6!";
    public String alreadyDisabled = "&4Blad: &cCzat jest juz wylaczony!";
    public String alreadyEnabled = "&4Blad: &cCzat jest juz wlaczony!";
    public String slowModeSet = "&8» &aSlowmode został ustawiony na {SLOWMODE}";
    public String slowMode = "&8» &cNastępną wiadomość możesz wysłać za: &6{TIME}&c!";
    public String disable = "&8» &cCzat jest aktualnie wyłaczony!";
    public String noCommand = "&8» &cKomenda &e{COMMAND} &cnie istnieje!";

}
