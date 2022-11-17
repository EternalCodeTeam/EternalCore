package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.publish.Content;
import com.eternalcode.core.user.User;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

class PrivateMessage implements Content {

    private final User sender;
    private final User target;
    private final String message;
    private final Collection<UUID> spies;
    private final boolean ignored;

    public PrivateMessage(User sender, User target, String message, Collection<UUID> spies, boolean ignored) {
        this.sender = sender;
        this.target = target;
        this.message = message;
        this.spies = spies;
        this.ignored = ignored;
    }

    public User getSender() {
        return this.sender;
    }

    public User getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }

    public Collection<UUID> getSpies() {
        return Collections.unmodifiableCollection(this.spies);
    }

    public boolean isIgnored() {
        return this.ignored;
    }

}
