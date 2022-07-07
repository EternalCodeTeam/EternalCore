package com.eternalcode.core.viewer;

import com.eternalcode.core.language.Language;

import java.util.UUID;
import java.util.function.Supplier;

class ViewerImpl implements Viewer {

    private final static ViewerImpl CONSOLE = new ViewerImpl(UUID.nameUUIDFromBytes("CONSOLE".getBytes()), true, Language.DEFAULT);

    private final UUID uuid;
    private final boolean console;

    private final Supplier<Language> language;

    private ViewerImpl(UUID uuid, boolean console, Language language) {
        this(uuid, console, () -> language);
    }

    private ViewerImpl(UUID uuid, boolean console, Supplier<Language> language) {
        this.uuid = uuid;
        this.console = console;
        this.language = language;
    }

    public static ViewerImpl console() {
        return CONSOLE;
    }

    public static ViewerImpl player(UUID uuid, Language language) {
        return new ViewerImpl(uuid, false, language);
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    public boolean isConsole() {
        return this.console;
    }

    public Language getLanguage() {
        return this.language.get();
    }

}
