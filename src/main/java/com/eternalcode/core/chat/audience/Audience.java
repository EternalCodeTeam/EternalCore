package com.eternalcode.core.chat.audience;

import com.eternalcode.core.language.Language;
import net.kyori.adventure.text.Component;

import java.util.function.Supplier;

public class Audience {

    private final net.kyori.adventure.audience.Audience audience;
    private final Supplier<Language> language;

    public Audience(net.kyori.adventure.audience.Audience audience, Language language) {
        this.audience = audience;
        this.language = () -> language;
    }

    public Audience(net.kyori.adventure.audience.Audience audience, Supplier<Language> language) {
        this.audience = audience;
        this.language = language;
    }

    public net.kyori.adventure.audience.Audience getAdventureAudience() {
        return audience;
    }

    public Language getLanguage() {
        return language.get();
    }

    public void message(Component component) {
        this.audience.sendMessage(component);
    }

}
