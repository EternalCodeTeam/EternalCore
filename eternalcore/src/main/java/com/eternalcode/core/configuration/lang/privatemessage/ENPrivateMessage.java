package com.eternalcode.core.configuration.lang.privatemessage;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENPrivateMessage implements Messages.PrivateMessage {

    public String noReply = "&8 » &cNie masz komu odpowiedzieć";
    public String privateMessageSendFormat = "&8[&7You -> &f{TARGET}&8]&7: &f{MESSAGE}";
    public String privateMessageReceiveFormat = "&8[&7{SENDER} -> &fYou&8]&7: &f{MESSAGE}";

    public String noReply() {
        return this.noReply;
    }

    public String sendFormat() {
        return this.privateMessageSendFormat;
    }

    public String receiveFormat() {
        return this.privateMessageReceiveFormat;
    }

}
