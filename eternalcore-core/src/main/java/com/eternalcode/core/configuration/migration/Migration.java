package com.eternalcode.core.configuration.migration;

import java.io.File;
import java.nio.file.Path;

public interface Migration {
    int migrationNumber();

    String file();

    String getDescription();

    MigrationStep[] getSteps();
}
