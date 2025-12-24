package com.eternalcode.core.feature.chat;

import java.time.Duration;
import java.util.Map;

public interface ChatSettings {

    boolean replaceStandardHelpMessage();

    boolean chatEnabled();

    Duration chatDelay();

    Map<String, Duration> chatCooldowns();

    int linesToClear();

    ChatSettings chatEnabled(boolean enabled);

    ChatSettings chatDelay(Duration delay);

    ChatSettings linesToClear(int lines);
}
