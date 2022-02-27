package com.eternalcode.core.chat.audience;

import com.eternalcode.core.language.Language;
import net.kyori.adventure.text.Component;

public class Audience {

    private final net.kyori.adventure.audience.Audience audience;
    private final Language language;

    public Audience(net.kyori.adventure.audience.Audience audience, Language language) {
        this.audience = audience;
        this.language = language;
    }

    public net.kyori.adventure.audience.Audience getAdventureAudience() {
        return audience;
    }

    public Language getLanguage() {
        return language;
    }

    public void message(Component component) {
        this.audience.sendMessage(component);
    }

}
