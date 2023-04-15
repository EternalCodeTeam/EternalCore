package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.user.User;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

record PrivateMessage(User sender, User target, String message, Collection<UUID> spies, boolean ignored) {

    @Override
    public Collection<UUID> spies() {
        return Collections.unmodifiableCollection(this.spies);
    }

}
