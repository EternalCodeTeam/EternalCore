package com.eternalcode.core.configuration.migration;

public abstract class AbstractMigration implements Migration {

    private final String description;
    private final MigrationStep[] steps;

    public AbstractMigration(String description, MigrationStep... steps) {
        this.description = description;
        this.steps = steps;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public MigrationStep[] getSteps() {
        return this.steps;
    }
}
