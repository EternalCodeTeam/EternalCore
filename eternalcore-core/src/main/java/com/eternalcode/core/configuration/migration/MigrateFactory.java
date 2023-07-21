package com.eternalcode.core.configuration.migration;

import com.eternalcode.core.configuration.ConfigurationManager;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MigrateFactory {

    private final List<Migration> migrations;
    private final MigrateService migrationService;

    private MigrateFactory(Plugin plugin, ConfigurationManager configurationManager) {
        this.migrations = new ArrayList<>();
        this.migrationService = new MigrateService(plugin, configurationManager);
    }

    public static MigrateFactory create(Plugin plugin, ConfigurationManager configurationManager) {
        return new MigrateFactory(plugin, configurationManager);
    }

    public MigrateFactory withMigration(Migration migration) {
        this.migrations.add(migration);
        return this;
    }

    public void migrate() {
        this.migrations.sort(Comparator.comparingInt(Migration::migrationNumber));

        for (Migration migration : this.migrations) {
            this.migrationService.migrateSingleMigration(migration);
        }
    }
}
