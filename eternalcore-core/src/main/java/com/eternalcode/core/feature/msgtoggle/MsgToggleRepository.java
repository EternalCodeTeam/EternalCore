package com.eternalcode.core.feature.msgtoggle;

import java.util.UUID;

interface MsgToggleRepository {

    boolean isToggledOff(UUID uuid);

    void setToggledOff(UUID uuid, boolean toggledOff);

    void remove(UUID uuid);

    void removeAll();

}
