package com.eternalcode.core.feature.chat;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ChatConfig extends OkaeriConfig implements ChatSettings {

    @Comment("# Custom message for unknown command")
    public boolean replaceStandardHelpMessage = false;

    @Comment("# Chat delay to send next message in chat")
    public Duration chatDelay = Duration.ofSeconds(5);

    @Comment("# Number of lines that will be cleared when using the /chat clear command")
    public int linesToClear = 256;

    @Comment("# Chat should be enabled?")
    public boolean chatEnabled = true;

    @Override
    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    @Override
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    @Override
    public Duration getChatDelay() {
        return this.chatDelay;
    }

    @Override
    public void setChatDelay(Duration chatDelay) {
        this.chatDelay = chatDelay;
    }
}
