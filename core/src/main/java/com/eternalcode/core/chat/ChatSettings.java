package com.eternalcode.core.chat;

import com.eternalcode.core.delay.DelaySettings;

import java.time.Duration;

public interface ChatSettings extends DelaySettings {

    boolean isChatEnabled();

    void setChatEnabled(boolean chatEnabled);

    Duration getChatDelay();

    void setChatDelay(Duration chatDelay);

    @Override
    default Duration delay() {
        return getChatDelay();
    }

}
