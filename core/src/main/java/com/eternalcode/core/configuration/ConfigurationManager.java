package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.LanguageComposer;
import com.eternalcode.core.configuration.composer.NotificationComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.shared.Position;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {

    private static final String BACKUP_FOLDER_NAME = "backup";

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Duration.class, new DurationComposer())
        .withComposer(Language.class, new LanguageComposer())
        .withComposer(Position.class, new PositionComposer())
        .withComposer(Notification.class, new NotificationComposer())
        .withMemberResolver(Visibility.PACKAGE_PRIVATE)
        .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        this.cdn.load(config.resource(this.dataFolder), config)
            .orThrow(RuntimeException::new);

        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public <T extends ReloadableConfig> void save(T config) {
        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);
    }

    public void reload() {
        this.createBackup();

        for (ReloadableConfig config : this.configs) {
            this.load(config);
        }
    }

    void createBackup() {
        File backupFolder = new File(this.dataFolder, BACKUP_FOLDER_NAME);

        if (!backupFolder.exists()) {
            backupFolder.mkdirs();
        }

        LocalDate currentDate = LocalDate.now();
        File currentBackupFolder = new File(backupFolder, currentDate.toString());
        if (!currentBackupFolder.exists()) {
            currentBackupFolder.mkdirs();
        }

        File[] filesToBackup = this.dataFolder.listFiles((dir, name) -> !name.equals(BACKUP_FOLDER_NAME));

        if (filesToBackup == null) {
            return;
        }

        for (File file : filesToBackup) {
            File backupFile = new File(currentBackupFolder, file.getName() + ".bak");

            try {
                Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        this.deleteIfOlderDirectory(backupFolder);
    }

    void deleteIfOlderDirectory(File backupFolder) {
        File[] backupFolders = backupFolder.listFiles(File::isDirectory);

        if (backupFolders != null) {
            for (File folder : backupFolders) {
                try {
                    LocalDate folderDate = LocalDate.parse(folder.getName());
                    LocalDate currentDate = LocalDate.now();

                    long days = ChronoUnit.DAYS.between(folderDate, currentDate);

                    if (days > 3) {
                        FileUtils.deleteDirectory(folder);
                    }
                }
                catch (DateTimeParseException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

}
