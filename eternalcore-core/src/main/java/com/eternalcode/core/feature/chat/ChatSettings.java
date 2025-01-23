package com.eternalcode.core.feature.chat;

import java.time.Duration;

public interface ChatSettings {

    boolean isChatEnabled();

    void setChatEnabled(boolean chatEnabled);

    Duration getChatDelay();

    void setChatDelay(Duration chatDelay);

    int linesToClear();

}
