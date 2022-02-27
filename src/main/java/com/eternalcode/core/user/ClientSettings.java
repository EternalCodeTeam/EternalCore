package com.eternalcode.core.user;

import java.util.Locale;

public interface ClientSettings {

    Locale getLocate();

    ClientSettings NONE = new ClientNoneSettings();

}
