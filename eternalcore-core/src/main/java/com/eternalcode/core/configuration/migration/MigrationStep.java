package com.eternalcode.core.configuration.migration;

public class MigrationStep {

    private final String oldKey;
    private final String newKey;

    public MigrationStep(String oldKey, String newKey) {
        this.oldKey = oldKey;
        this.newKey = newKey;
    }

    public String getOldKey() {
        return this.oldKey;
    }

    public String getNewKey() {
        return this.newKey;
    }

    public static MigrationStep move(String oldKey, String newKey) {
        return new MigrationStep(oldKey, newKey);
    }
}
