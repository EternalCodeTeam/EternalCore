package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * One file per kit: {@code plugins/EternalCore/kits/<kit name>.yml}.
 * Kit names are validated by {@link Kit#isValidName(String)}, so they are always safe file names.
 */
@Repository
class KitRepositoryYaml implements KitRepository {

    private static final String KITS_DIRECTORY = "kits";
    private static final String EXAMPLE_KIT_NAME = "starter";
    private static final Duration EXAMPLE_KIT_COOLDOWN = Duration.ofHours(1);

    private final Object fileLock = new Object();

    private final File kitsDirectory;
    private final KitSettings settings;
    private final Scheduler scheduler;
    private final Logger logger;

    @Inject
    KitRepositoryYaml(File dataFolder, KitSettings settings, Scheduler scheduler, Logger logger) {
        this.kitsDirectory = new File(dataFolder, KITS_DIRECTORY);
        this.settings = settings;
        this.scheduler = scheduler;
        this.logger = logger;
    }

    @Override
    public CompletableFuture<List<Kit>> findAll() {
        return this.scheduler.completeAsync(() -> {
            synchronized (this.fileLock) {
                if (!this.kitsDirectory.exists()) {
                    this.createDirectoryWithExample();
                }

                return this.readKits();
            }
        });
    }

    @Override
    public CompletableFuture<Void> save(Kit kit) {
        return this.scheduler.completeAsync(() -> {
            synchronized (this.fileLock) {
                this.writeKit(kit);
                return null;
            }
        });
    }

    @Override
    public CompletableFuture<Void> delete(String kitName) {
        return this.scheduler.completeAsync(() -> {
            synchronized (this.fileLock) {
                try {
                    Files.deleteIfExists(this.kitFile(kitName).toPath());
                    return null;
                }
                catch (IOException exception) {
                    throw new UncheckedIOException("Failed to delete kit file of " + kitName, exception);
                }
            }
        });
    }

    private List<Kit> readKits() {
        File[] files = this.kitsDirectory.listFiles((directory, fileName) -> fileName.endsWith(KitFileSchema.FILE_EXTENSION));

        if (files == null) {
            return List.of();
        }

        List<Kit> kits = new ArrayList<>(files.length);

        for (File file : files) {
            String kitName = file.getName().substring(0, file.getName().length() - KitFileSchema.FILE_EXTENSION.length());

            if (!Kit.isValidName(kitName)) {
                this.logger.warning("Skipping kit file with invalid name: " + file.getName());
                continue;
            }

            try {
                kits.add(KitFileSchema.read(kitName, YamlConfiguration.loadConfiguration(file)));
            }
            catch (RuntimeException exception) {
                this.logger.log(Level.SEVERE, "Failed to load kit file " + file.getName(), exception);
            }
        }

        return kits;
    }

    private void writeKit(Kit kit) {
        try {
            Files.createDirectories(this.kitsDirectory.toPath());
            KitFileSchema.write(kit).save(this.kitFile(kit.name()));
        }
        catch (IOException exception) {
            throw new UncheckedIOException("Failed to save kit file of " + kit.name(), exception);
        }
    }

    private void createDirectoryWithExample() {
        Kit example = Kit.builder(EXAMPLE_KIT_NAME)
            .displayName("<green>Starter")
            .cooldown(EXAMPLE_KIT_COOLDOWN)
            .permission(this.settings.defaultPermissionPrefix() + EXAMPLE_KIT_NAME)
            .icon(new ItemStack(Material.STONE_SWORD))
            .items(List.of(
                new ItemStack(Material.STONE_SWORD),
                new ItemStack(Material.STONE_PICKAXE),
                new ItemStack(Material.COOKED_BEEF, 16)
            ))
            .build();

        this.writeKit(example);
    }

    private File kitFile(String kitName) {
        return new File(this.kitsDirectory, kitName + KitFileSchema.FILE_EXTENSION);
    }
}
