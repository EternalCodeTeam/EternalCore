package com.eternalcode.core.user.client;

import java.util.Locale;

class ClientNoneSettings implements ClientSettings {

    @Override
    public Locale getLocate() {
        return Locale.ENGLISH;
    }

    @Override
    public boolean isOnline() {
        return false;
    }


}
