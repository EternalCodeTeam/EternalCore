package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.publish.Content;
import com.eternalcode.core.user.User;

import java.util.Collection;
import java.util.UUID;

class PrivateMessage implements Content {

    private final User sender;
    private final User target;
    private final String message;
    private final Collection<UUID> spy;
    private final boolean ignored;

    public PrivateMessage(User sender, User target, String message, Collection<UUID> spy, boolean ignored) {
        this.sender = sender;
        this.target = target;
        this.message = message;
        this.spy = spy;
        this.ignored = ignored;
    }

    public User getSender() {
        return sender;
    }

    public User getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

    public Collection<UUID> getSpyUuids() {
        return spy;
    }

    public boolean isIgnored() {
        return ignored;
    }

}
