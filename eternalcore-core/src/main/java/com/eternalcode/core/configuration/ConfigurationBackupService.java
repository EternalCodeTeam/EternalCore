package com.eternalcode.core.configuration;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@FeatureDocs(
        name = "Configuration Backup",
        description = "Backs up the full configuration to prevent config destruction, backup is only 3 days back"
)
public class ConfigurationBackupService {

    private static final String BACKUP_FOLDER_NAME = "backup";
    private static final String BACKUP_FILE_EXTENSION = ".bak";
    private final File dataFolder;

    public ConfigurationBackupService(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void createBackup() {
        File backupFolder = new File(this.dataFolder, BACKUP_FOLDER_NAME);

        if (!backupFolder.exists()) {
            backupFolder.mkdirs();
        }

        LocalDate currentDate = LocalDate.now();
        File currentBackupFolder = new File(backupFolder, currentDate.toString());
        if (!currentBackupFolder.exists()) {
            currentBackupFolder.mkdirs();
        }

        this.copyFolderContents(this.dataFolder, currentBackupFolder);
        this.deleteIfOlderDirectory(backupFolder);
    }

    private void copyFolderContents(File sourceFolder, File targetFolder) {
        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            return;
        }

        if (!targetFolder.exists()) {
            boolean targetFolderCreated = targetFolder.mkdirs();

            if (!targetFolderCreated) {
                return;
            }

        }

        File[] filesToBackup = sourceFolder.listFiles();
        if (filesToBackup == null) {
            return;
        }

        for (File file : filesToBackup) {
            if (file.isDirectory() && !file.getName().equals(BACKUP_FOLDER_NAME)) {
                File subFolder = new File(targetFolder, file.getName());
                this.copyFolderContents(file, subFolder);

                continue;
            }

            if (file.isFile() && !file.getName().endsWith(BACKUP_FILE_EXTENSION)) {
                File backupFile = new File(targetFolder, file.getName() + BACKUP_FILE_EXTENSION);

                this.copyToBackupFile(file, backupFile);
            }
        }
    }

    private void copyToBackupFile(File targetFolder, File path) {
        try {
            Files.copy(targetFolder.toPath(), path.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // delete backup folders older than X days
    private void deleteIfOlderDirectory(File backupFolder) {
        File[] backupFolders = backupFolder.listFiles(File::isDirectory);

        if (backupFolders == null) {
            return;
        }

        for (File folder : backupFolders) {
            if (folder.getName().equals(BACKUP_FOLDER_NAME)) {
                return;
            }

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
