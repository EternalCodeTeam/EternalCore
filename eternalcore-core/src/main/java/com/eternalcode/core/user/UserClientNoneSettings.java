package com.eternalcode.core.user;

import java.util.Locale;

class UserClientNoneSettings implements UserClientSettings {

    @Override
    public Locale getLocate() {
        return Locale.ENGLISH;
    }

    @Override
    public boolean isOnline() {
        return false;
    }


}
