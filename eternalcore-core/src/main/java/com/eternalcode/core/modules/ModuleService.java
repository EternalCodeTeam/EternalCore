package com.eternalcode.core.modules;

import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.injector.annotations.lite.LiteContextual;
import com.eternalcode.core.injector.annotations.lite.LiteHandler;
import com.eternalcode.core.util.ReflectUtil;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class ModuleService {

    private static final String FEATURE_PACKAGE = "com.eternalcode.core.feature";
    private static final String FEATURE_PACKAGE_PREFIX = FEATURE_PACKAGE + ".";
    private static final String[] HEADER = ModulesConfig.class.getAnnotation(Header.class).value();

    private static final Set<Class<? extends Annotation>> GATED_ANNOTATIONS = Set.of(
        Controller.class,
        Command.class,
        RootCommand.class,
        LiteArgument.class,
        LiteHandler.class,
        LiteContextual.class,
        LiteCommandEditor.class
    );

    private final File file;
    private final ModulesConfig config;
    private final Logger logger;
    private final Map<String, String> descriptions = new LinkedHashMap<>();

    public ModuleService(File dataFolder, Logger logger) {
        this.logger = logger;
        this.file = new File(dataFolder, "modules.yml");
        this.config = new ModulesConfig();
        this.config.withConfigurer(new YamlSnakeYamlConfigurer()).withBindFile(this.file);

        if (this.file.exists()) {
            this.config.load(true);
        }
    }

    public boolean isEnabled(Class<?> type) {
        if (!isGated(type)) {
            return true;
        }

        String moduleId = resolveModuleId(type);

        return moduleId == null || this.config.modules.getOrDefault(moduleId, true);
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

    public void logDisabledModules() {
        for (Map.Entry<String, Boolean> entry : this.config.modules.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                this.logger.info("Module '" + entry.getKey() + "' is disabled in modules.yml, its commands and listeners will not be registered.");
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

    private static boolean isGated(Class<?> type) {
        for (Class<? extends Annotation> annotationType : GATED_ANNOTATIONS) {
            if (type.isAnnotationPresent(annotationType)) {
                return true;
            }
        }

        return false;
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
