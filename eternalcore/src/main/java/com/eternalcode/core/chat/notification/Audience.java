package com.eternalcode.core.chat.notification;

import com.eternalcode.core.language.Language;

import java.util.UUID;
import java.util.function.Supplier;

public class Audience {

    private final static Audience CONSOLE = new Audience(UUID.nameUUIDFromBytes("CONSOLE".getBytes()), true, Language.DEFAULT);

    private final UUID uuid;
    private final boolean console;

    private final Supplier<Language> language;

    private Audience(UUID uuid, boolean console, Language language) {
        this(uuid, console, () -> language);
    }

    private Audience(UUID uuid, boolean console, Supplier<Language> language) {
        this.uuid = uuid;
        this.console = console;
        this.language = language;
    }

    public static Audience console() {
        return CONSOLE;
    }

    public static Audience player(UUID uuid, Language language) {
        return new Audience(uuid, false, language);
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isConsole() {
        return console;
    }

    public Language getLanguage() {
        return language.get();
    }

}
