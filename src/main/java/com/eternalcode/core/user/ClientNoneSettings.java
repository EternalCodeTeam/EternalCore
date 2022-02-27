package com.eternalcode.core.user;

import java.util.Locale;

class ClientNoneSettings implements ClientSettings {

    @Override
    public Locale getLocate() {
        return Locale.ENGLISH;
    }

}
