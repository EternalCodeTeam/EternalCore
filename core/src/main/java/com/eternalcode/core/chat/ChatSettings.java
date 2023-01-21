package com.eternalcode.core.chat;

import java.time.Duration;

public interface ChatSettings {

    boolean isChatEnabled();

    void setChatEnabled(boolean chatEnabled);

    Duration getChatDelay();

    void setChatDelay(Duration chatDelay);

}
