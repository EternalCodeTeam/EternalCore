package com.eternalcode.core.user;

public interface UserClientSettings {

    UserClientSettings NONE = new UserClientNoneSettings();

    boolean isOnline();

    default boolean isOffline() {
        return !this.isOnline();
    }

}
