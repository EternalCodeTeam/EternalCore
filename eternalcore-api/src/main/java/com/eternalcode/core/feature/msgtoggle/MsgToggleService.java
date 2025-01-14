package com.eternalcode.core.feature.msgtoggle;

import java.util.UUID;

public interface MsgToggleService {

    boolean hasMsgToggledOff(UUID uuid);

    void toggleMsg(UUID uuid, boolean toggle);

}
