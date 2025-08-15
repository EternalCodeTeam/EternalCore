package com.eternalcode.core.test;

import com.eternalcode.core.viewer.Viewer;
import java.util.Objects;
import java.util.UUID;

public class MockViewer implements Viewer {

    private final String name;
    private final UUID uuid;
    private final boolean console;

    private MockViewer(String name, UUID uuid, boolean console) {
        this.name = name;
        this.uuid = uuid;
        this.console = console;
    }

    public static MockViewer player(String name, UUID uuid) {
        return new MockViewer(name, uuid, false);
    }

    public static MockViewer console() {
        return new MockViewer("CONSOLE", UUID.nameUUIDFromBytes(new byte[0]), true);
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isConsole() {
        return this.console;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MockViewer that)) {
            return false;
        }
        return console == that.console && Objects.equals(name, that.name) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, uuid, console);
    }
}
