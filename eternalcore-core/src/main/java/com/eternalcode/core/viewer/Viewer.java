package com.eternalcode.core.viewer;

import java.util.UUID;

public interface Viewer {

    UUID getUniqueId();

    boolean isConsole();

    String getName();

}
