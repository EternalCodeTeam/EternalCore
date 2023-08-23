package com.eternalcode.core.configuration.migration;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.file.Path;

public class MigrateService {

    private final Plugin plugin;
    private final ConfigurationManager configurationManager;

    public MigrateService(Plugin plugin, ConfigurationManager configurationManager) {
        this.plugin = plugin;
        this.configurationManager = configurationManager;
    }

    public void migrateSingleMigration(Migration migration) {
        Path filePath = Path.of(this.plugin.getDataFolder() + File.separator + migration.file());
        MigrationStep[] steps = migration.getSteps();

        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(filePath.toFile());

            for (MigrationStep step : steps) {
                this.updateYamlData(config, step.getOldKey(), step.getNewKey());
            }

            this.plugin.getLogger().info(String.format("Applying migration: %s", migration.getDescription()));

            this.configurationManager.save(new PluginConfiguration());

            this.plugin.getLogger().info("Successfully saved configuration after migrations");
        }
        catch (Exception exception) {
            throw new MigrateException("An error occurred while migrating the configuration file", exception);
        }
    }

    private void updateYamlData(ConfigurationSection section, String oldKey, String newKey) {
        if (section.contains(oldKey)) {
            Object value = section.get(oldKey);

            section.set(oldKey, null);
            section.set(newKey, value);
        }
    }
}
