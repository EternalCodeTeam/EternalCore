package com.eternalcode.core.feature.privatechat;

import java.util.UUID;

public interface PrivateChatService {

    void enableSpy(UUID uuid);

    void disableSpy(UUID uuid);

    boolean isSpy(UUID uuid);
}
