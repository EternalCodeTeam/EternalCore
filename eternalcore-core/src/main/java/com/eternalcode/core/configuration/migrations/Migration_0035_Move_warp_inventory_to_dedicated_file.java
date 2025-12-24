package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.migrate.ConfigMigration;
import eu.okaeri.configs.migrate.view.RawConfigView;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class Migration_0035_Move_warp_inventory_to_dedicated_file implements ConfigMigration {

    private static final Logger LOGGER = Logger
            .getLogger(Migration_0035_Move_warp_inventory_to_dedicated_file.class.getName());
    private static final String ROOT_KEY = "warpInventory";
    private static final String NESTED_KEY = "warp.warpInventory";
    private static final String NESTED_SECTION = "warp";

    @Override
    public boolean migrate(@NonNull OkaeriConfig config, @NonNull RawConfigView view) {
        String foundKey = null;
        Map<String, Object> warpInventory = this.getFromView(view, ROOT_KEY);

        if (warpInventory != null) {
            foundKey = ROOT_KEY;
        } else {
            warpInventory = this.getFromView(view, NESTED_KEY);
            if (warpInventory != null) {
                foundKey = NESTED_KEY;
            }
        }

        if (warpInventory == null) {
            warpInventory = this.getFromFileFallback(config);
        }

        if (warpInventory == null) {
            return false;
        }

        Map<String, Object> newContent = this.transformData(warpInventory);

        if (!this.saveNewConfig(config, newContent)) {
            return false;
        }

        if (foundKey != null) {
            view.remove(foundKey);
        } else {
            view.remove(ROOT_KEY);
            view.remove(NESTED_KEY);
        }

        return true;
    }

    private Map<String, Object> getFromView(RawConfigView view, String key) {
        if (!view.exists(key)) {
            return null;
        }
        Object obj = view.get(key);
        return obj instanceof Map ? (Map<String, Object>) obj : null;
    }

    private Map<String, Object> getFromFileFallback(OkaeriConfig config) {
        File bindFile = config.getBindFile().toFile();
        if (bindFile == null || !bindFile.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(bindFile)) {
            Map<String, Object> content = new Yaml().load(reader);
            if (content == null) {
                return null;
            }

            if (content.containsKey(ROOT_KEY) && content.get(ROOT_KEY) instanceof Map) {
                return (Map<String, Object>) content.get(ROOT_KEY);
            }

            if (content.containsKey(NESTED_SECTION) && content.get(NESTED_SECTION) instanceof Map) {
                Map<String, Object> warpSection = (Map<String, Object>) content.get(NESTED_SECTION);
                if (warpSection.containsKey(ROOT_KEY) && warpSection.get(ROOT_KEY) instanceof Map) {
                    return (Map<String, Object>) warpSection.get(ROOT_KEY);
                }
            }
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Failed to read configuration file: " + bindFile.getAbsolutePath(), exception);
        }
        return null;
    }

    private Map<String, Object> transformData(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();

        if (source.containsKey("title")) {
            Map<String, Object> display = new LinkedHashMap<>();
            display.put("title", source.get("title"));
            result.put("display", display);
        }

        this.copyIfPresent(source, result, "items");
        this.copyIfPresent(source, result, "border");
        this.copyIfPresent(source, result, "decorationItems");

        return result;
    }

    private void copyIfPresent(Map<String, Object> source, Map<String, Object> target, String key) {
        if (source.containsKey(key)) {
            target.put(key, source.get(key));
        }
    }

    private boolean saveNewConfig(OkaeriConfig config, Map<String, Object> content) {
        File destFile = this.getDestinationFile(config);
        if (destFile == null) {
            return false;
        }

        if (destFile.exists() && !this.targetIsSafeToWrite(destFile)) {
            return true;
        }

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);
        options.setIndicatorIndent(0);
        options.setSplitLines(false);

        try (FileWriter writer = new FileWriter(destFile)) {
            new Yaml(options).dump(content, writer);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save new configuration file: " + destFile.getAbsolutePath(), e);
            return false;
        }
    }

    private File getDestinationFile(OkaeriConfig config) {
        File bindFile = config.getBindFile().toFile();
        if (bindFile == null) {
            return null;
        }
        File dataFolder = bindFile.getParentFile();
        if ("lang".equals(dataFolder.getName())) {
            dataFolder = dataFolder.getParentFile();
        }
        return new File(dataFolder, "warp-inventory.yml");
    }

    private boolean targetIsSafeToWrite(File file) {
        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> existing = new Yaml().load(reader);
            if (existing == null || !existing.containsKey("items")) {
                return true;
            }
            Object items = existing.get("items");
            return items instanceof Map && ((Map<?, ?>) items).isEmpty();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Failed to check if target file is safe to write: " + file.getAbsolutePath(),
                    exception);
            return false;
        }
    }
}
