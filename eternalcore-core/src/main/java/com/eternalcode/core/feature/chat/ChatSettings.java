package com.eternalcode.core.feature.chat;

import com.eternalcode.multification.notice.Notice;

import java.time.Duration;

public interface ChatSettings {

    boolean replaceStandardHelpMessage();

    boolean chatEnabled();

    Duration chatDelay();

    int linesToClear();

    ChatSettings chatEnabled(boolean enabled);

    ChatSettings chatDelay(Duration delay);

    ChatSettings linesToClear(int lines);

    Notice chatNotice();
}
