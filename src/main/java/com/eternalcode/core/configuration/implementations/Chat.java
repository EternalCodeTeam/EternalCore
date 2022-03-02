package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.chat.ChatSettings;
import com.eternalcode.core.language.Language;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;

import java.util.Arrays;
import java.util.List;

@Contextual
public class Chat implements ChatSettings {

    public Language defaultLanguage = Language.EN;
    public List<Language> languages = Arrays.asList(Language.EN, Language.PL);

    public double helpopCooldown = 60.0;
    public boolean commandExact = false;
    public double chatDelay = 5.0;
    public boolean chatEnabled = true;

    @Override @Exclude
    public boolean isChatEnabled() {
        return this.chatEnabled;
    }

    @Override @Exclude
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    @Override @Exclude
    public double getChatDelay() {
        return chatDelay;
    }

    @Override @Exclude
    public void setChatDelay(double chatDelay) {
        this.chatDelay = chatDelay;
    }

}
