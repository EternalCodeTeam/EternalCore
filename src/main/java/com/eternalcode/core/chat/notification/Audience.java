package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Language;

import java.util.UUID;
import java.util.function.Supplier;

public class Audience {

    private final Supplier<Language> language;
    private final UUID uuid;
    private final boolean console;

    private Audience(Language language, boolean console, UUID uuid) {
        this(() -> language, console, uuid);
    }

    private Audience(Supplier<Language> language, boolean console, UUID uuid) {
        this.language = language;
        this.console = console;
        this.uuid = uuid;
    }

    public Language getLanguage() {
        return language.get();
    }

    public boolean isConsole() {
        return console;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Audience console() {
        return new Audience(Language.DEFAULT, true, UUID.nameUUIDFromBytes("console".getBytes()));
    }

    public static Audience player(UUID uuid, Language language) {
        return new Audience(language, false, uuid);
    }

}
