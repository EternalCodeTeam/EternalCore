package com.eternalcode.core.configuration.migration;

import java.nio.file.Path;

public interface Migration {
    int migrationNumber();

    Path filePath();

    String oldValue();

    String newValue();
}
