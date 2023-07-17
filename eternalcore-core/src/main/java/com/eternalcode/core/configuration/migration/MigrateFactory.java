package com.eternalcode.core.configuration.migration;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MigrateFactory {

    private final List<Migration> migrations;

    private MigrateFactory() {
        this.migrations = new ArrayList<>();
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull MigrateFactory create() {
        return new MigrateFactory();
    }

    public MigrateFactory withMigration(Migration migration) {
        this.migrations.add(migration);
        return this;
    }

    public void migrate() {
        this.migrations.sort(Comparator.comparingInt(Migration::migrationNumber));

        for (Migration migration : this.migrations) {
            migrateSingleMigration(migration);
        }
    }

    private void migrateSingleMigration(@NotNull Migration migration) {
        Path filePath = migration.filePath();
        String oldValue = migration.oldValue();
        String newValue = migration.newValue();

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<String> updatedLines = updateLines(lines, oldValue, newValue);

            Files.write(filePath, updatedLines);
        }
        catch (Exception exception) {
            throw new MigrateException("An error occurred while migrating the configuration file", exception);
        }
    }

    private @NotNull List<String> updateLines(@NotNull List<String> lines, String oldValue, String newValue) {
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            if (line.contains(oldValue)) {
                line = line.replace(oldValue, newValue);
            }
            updatedLines.add(line);
        }

        return updatedLines;
    }

}
