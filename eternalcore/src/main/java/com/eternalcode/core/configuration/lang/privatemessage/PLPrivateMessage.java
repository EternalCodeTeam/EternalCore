package com.eternalcode.core.configuration.lang.privatemessage;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLPrivateMessage implements Messages.PrivateMessage {

    public String noReply = "&8 » &cYou have no one to reply!";
    public String privateMessageSendFormat = "&8[&7Ty -> &f{TARGET}&8]&7: &f{MESSAGE}";
    public String privateMessageReceiveFormat = "&8[&7{SENDER} -> &fTy&8]&7: &f{MESSAGE}";

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
