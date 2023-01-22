package com.eternalcode.core.user.client;

import java.util.Locale;

public interface ClientSettings {

    Locale getLocate();

    ClientSettings NONE = new ClientNoneSettings();

    boolean isOnline();

    default boolean isOffline() {
        return !isOnline();
    }

}
