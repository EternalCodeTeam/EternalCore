package com.eternalcode.core.modules;

import com.eternalcode.core.util.ReflectUtil;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class ModuleService {

    private static final String FEATURE_PACKAGE = "com.eternalcode.core.feature";
    private static final String FEATURE_PACKAGE_PREFIX = FEATURE_PACKAGE + ".";
    private static final String[] HEADER = ModulesConfig.class.getAnnotation(Header.class).value();

    private final File file;
    private final ModulesConfig config;
    private final Logger logger;
    private final Map<String, String> descriptions = new LinkedHashMap<>();
    private Map<String, Boolean> effectiveState;

    public ModuleService(File dataFolder, Logger logger) {
        this.logger = logger;
        this.file = new File(dataFolder, "modules.yml");
        this.config = new ModulesConfig();
        this.config.withConfigurer(new YamlSnakeYamlConfigurer()).withBindFile(this.file);

        if (this.file.exists()) {
            this.config.load(true);
        }

        this.effectiveState = new LinkedHashMap<>(this.config.modules);
    }

    public boolean isEnabled(Class<?> type) {
        String moduleId = resolveModuleId(type);

        return moduleId == null || this.effectiveState.getOrDefault(moduleId, true);
    }

    public void registerDiscoveredModules(Collection<Class<?>> discoveredTypes) {
        Set<String> discoveredModules = new TreeSet<>();

        for (Class<?> type : discoveredTypes) {
            String moduleId = resolveModuleId(type);

            if (moduleId != null) {
                discoveredModules.add(moduleId);
            }
        }

        for (String moduleId : discoveredModules) {
            this.config.modules.putIfAbsent(moduleId, true);
        }
    }

    public void loadModuleDescriptions(ClassLoader classLoader) {
        for (Class<?> type : ReflectUtil.scanClasses(FEATURE_PACKAGE, classLoader)) {
            ModuleDescription description = type.getAnnotation(ModuleDescription.class);

            if (description == null) {
                continue;
            }

            String moduleId = resolveModuleId(type);

            if (moduleId != null) {
                this.descriptions.put(moduleId, description.value());
            }
        }
    }

    public void resolveEffectiveState(Collection<Class<?>> discoveredTypes) {
        ModuleDependencyGraph graph = ModuleDependencyGraph.build(discoveredTypes);
        Map<String, Boolean> effective = new LinkedHashMap<>(this.config.modules);

        boolean changed = true;
        while (changed) {
            changed = false;

            for (String moduleId : this.config.modules.keySet()) {
                if (effective.getOrDefault(moduleId, true)) {
                    continue;
                }

                List<String> blockingDependents = graph.dependentsOf(moduleId).stream()
                    .filter(dependent -> effective.getOrDefault(dependent, true))
                    .sorted()
                    .toList();

                if (blockingDependents.isEmpty()) {
                    continue;
                }

                effective.put(moduleId, true);
                changed = true;

                this.warnAboutConflict(moduleId, blockingDependents);
            }
        }

        this.effectiveState = effective;
    }

    public void logDisabledModules() {
        for (Map.Entry<String, Boolean> entry : this.effectiveState.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                this.logger.info("Module '" + entry.getKey() + "' is disabled in modules.yml, its commands, listeners, tasks and config will not be loaded.");
            }
        }
    }

    public void writeModulesFile() {
        StringBuilder content = new StringBuilder();

        for (String headerLine : HEADER) {
            content.append(headerLine).append(System.lineSeparator());
        }

        content.append(System.lineSeparator()).append("modules:").append(System.lineSeparator());

        for (Map.Entry<String, Boolean> entry : this.config.modules.entrySet()) {
            String description = this.descriptions.get(entry.getKey());

            if (description != null) {
                content.append("  # ").append(description).append(System.lineSeparator());
            }

            content.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append(System.lineSeparator());
        }

        this.writeIfChanged(content.toString());
    }

    private void writeIfChanged(String newContent) {
        try {
            if (this.file.exists() && newContent.equals(Files.readString(this.file.toPath(), StandardCharsets.UTF_8))) {
                return;
            }

            File parentFile = this.file.getParentFile();
            if (parentFile != null) {
                Files.createDirectories(parentFile.toPath());
            }

            Files.writeString(this.file.toPath(), newContent, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            this.logger.warning("Failed to write " + this.file.getName() + ": " + exception.getMessage());
        }
    }

    private void warnAboutConflict(String moduleId, List<String> blockingDependents) {
        String dependents = String.join(", ", blockingDependents);

        this.logger.warning("============================================================");
        this.logger.warning("You can't disable module '" + moduleId + "' - it is required by: " + dependents);
        this.logger.warning("Keeping '" + moduleId + "' ENABLED to avoid starting in a broken state.");
        this.logger.warning("If you really want to disable '" + moduleId + "', disable " + dependents + " in modules.yml too.");
        this.logger.warning("============================================================");
    }

    static String resolveModuleId(Class<?> type) {
        String packageName = type.getPackageName();

        if (!packageName.startsWith(FEATURE_PACKAGE_PREFIX)) {
            return null;
        }

        String remainder = packageName.substring(FEATURE_PACKAGE_PREFIX.length());
        int dotIndex = remainder.indexOf('.');

        return dotIndex == -1 ? remainder : remainder.substring(0, dotIndex);
    }

}
