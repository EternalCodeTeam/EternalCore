package com.eternalcode.core.viewer;

import com.eternalcode.core.user.User;
import java.util.UUID;

record UserViewer(User user) implements Viewer {

    @Override
    public UUID getUniqueId() {
        return this.user.getUniqueId();
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public String getName() {
        return this.user.getName();
    }
}
