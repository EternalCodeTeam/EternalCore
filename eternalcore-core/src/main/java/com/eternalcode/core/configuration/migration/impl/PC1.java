package com.eternalcode.core.configuration.migration.impl;

import com.eternalcode.core.configuration.migration.AbstractMigration;

import static com.eternalcode.core.configuration.migration.MigrationStep.move;

public class PC1 extends AbstractMigration {

    public PC1() {
        super("Cosmetic change for number of lines to clear.",
            move("chat.linesToClear", "chat.numberOfLinesToClear"));
    }

    @Override
    public int migrationNumber() {
        return 1;
    }

    @Override
    public String file() {
        return "config.yml";
    }
}
