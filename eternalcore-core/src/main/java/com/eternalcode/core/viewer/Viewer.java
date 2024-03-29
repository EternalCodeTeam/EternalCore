package com.eternalcode.core.viewer;

import com.eternalcode.core.feature.language.Language;

import java.util.UUID;

public interface Viewer {

    UUID getUniqueId();

    Language getLanguage();

    boolean isConsole();

    String getName();

}
