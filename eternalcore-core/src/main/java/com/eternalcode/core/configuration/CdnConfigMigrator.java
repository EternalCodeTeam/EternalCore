
package com.eternalcode.core.configuration;

import java.nio.file.Files;
import java.util.ArrayList;
import org.jetbrains.annotations.ApiStatus;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Deprecated(since = "2.0") // TODO: remove when there will be no use of the old version 1.0
class CdnConfigMigrator {

    private static final List<String> MAPS_TO_MIGRATE = List.of(
        "argument.gameModeAliases",
        "gameModeShortCuts",
        "commands",
        "homes.maxHomes",
        "autoMessage.messages",
        "warp.warpInventory.items",
        "event.deathMessageByDamageCause"
    );

    private final Yaml yaml;

    CdnConfigMigrator(Yaml yaml) {
        this.yaml = yaml;
    }

    void runMigrations(File file) {
        if (!file.exists()) {
            return;
        }

        try {
            String migratedLists = migrateListWithObjects(Files.readAllLines(file.toPath()));
            String migratedMaps = migrateEmptyMaps(migratedLists);
            Files.writeString(file.toPath(), migratedMaps);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String migrateListWithObjects(List<String> lines) {
        List<String> fixedLines = new ArrayList<>();
        int i = 0;

        while (i < lines.size()) {
            String line = lines.get(i);
            String trimmed = line.trim();

            if (trimmed.equals("- :") && i + 1 < lines.size()) {
                String currentIndent = line.substring(0, line.indexOf("-"));
                String nextLine = lines.get(i + 1);

                String merged = currentIndent + "- " + nextLine.stripLeading();
                fixedLines.add(merged);
                i += 2;
            }
            else {
                fixedLines.add(line);
                i++;
            }
        }

        return String.join(System.lineSeparator(), fixedLines);
    }

    private String migrateEmptyMaps(String migrated) {
        Map<String, Object> config = yaml.load(migrated);
        if (config == null) {
            return migrated;
        }

        for (String key : MAPS_TO_MIGRATE) {
            migrateEmptyMap(config, key);
        }

        return yaml.dump(config);
    }

    @SuppressWarnings("unchecked")
    private void migrateEmptyMap(Map<String, Object> config, String fullKey) {
        String[] keyParts = fullKey.split("\\.");
        Map<String, Object> currentMap = config;

        for (int i = 0; i < keyParts.length - 1; i++) {
            Object value = currentMap.get(keyParts[i]);
            if (!(value instanceof Map)) {
                return;
            }
            currentMap = (Map<String, Object>) value;
        }

        String finalKey = keyParts[keyParts.length - 1];
        Object value = currentMap.get(finalKey);

        if (value instanceof List && ((List<?>) value).isEmpty()) {
            currentMap.put(finalKey, new LinkedHashMap<>());
        }
    }

}
