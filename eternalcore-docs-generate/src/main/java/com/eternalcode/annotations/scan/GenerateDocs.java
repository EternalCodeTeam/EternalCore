package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.command.CommandResult;
import com.eternalcode.annotations.scan.command.CommandScanResolver;
import com.eternalcode.annotations.scan.permission.PermissionResult;
import com.eternalcode.annotations.scan.permission.PermissionScanResolver;
import com.eternalcode.annotations.scan.placeholder.PlaceholderResult;
import com.eternalcode.annotations.scan.placeholder.PlaceholderScanResolver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.Comparator.comparing;

public class GenerateDocs {

    private static final String TARGET_CLASS = "com.eternalcode.core.EternalCore";
    private static final String DOCS_FILE = "raw_eternalcore_documentation.json";
    private static final String PLACEHOLDERS_FILE = "raw_eternalcore_placeholders.json";

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create();

    public static void main(String[] args) {
        try {
            Class<?> targetClass = Class.forName(TARGET_CLASS);
            EternalScanner scanner = new EternalScanner(targetClass.getClassLoader(), targetClass.getPackage());

            List<CommandResult> commands = scanner.scan(new CommandScanResolver(), comparing(CommandResult::name));
            List<PermissionResult> permissions = scanner.scan(new PermissionScanResolver(), comparing(PermissionResult::name));
            List<PlaceholderResult> placeholders = scanner.scan(new PlaceholderScanResolver(), comparing(PlaceholderResult::name));

            writeJson(DOCS_FILE, new DocumentationResult(commands, permissions));
            writeJson(PLACEHOLDERS_FILE, placeholders);
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to generate documentation", exception);
        }
    }

    private static void writeJson(String fileName, Object data) throws IOException {
        Files.writeString(Path.of(fileName), GSON.toJson(data));
    }

    public record DocumentationResult(
        List<CommandResult> commands,
        List<PermissionResult> permissions
    ) {}
}
