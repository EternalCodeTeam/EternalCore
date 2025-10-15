package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.command.CommandResult;
import com.eternalcode.annotations.scan.command.CommandScanResolver;
import com.eternalcode.annotations.scan.permission.PermissionResult;
import com.eternalcode.annotations.scan.permission.PermissionScanResolver;
import com.eternalcode.annotations.scan.placeholder.PlaceholderResult;
import com.eternalcode.annotations.scan.placeholder.PlaceholderScanResolver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class GenerateDocs {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.eternalcode.core.EternalCore");
        EternalScanner scanner = new EternalScanner(aClass.getClassLoader(), aClass.getPackage());

        // Scan commands
        List<CommandResult> commandResults = scanner.scan(new CommandScanResolver())
            .stream()
            .sorted(Comparator.comparing(CommandResult::name))
            .distinct()
            .toList();

        // Scan permissions
        List<PermissionResult> permissionResults = scanner.scan(new PermissionScanResolver())
            .stream()
            .sorted(Comparator.comparing(PermissionResult::name))
            .distinct()
            .toList();

        // Scan placeholders
        List<PlaceholderResult> placeholderResults = scanner.scan(new PlaceholderScanResolver())
            .stream()
            .sorted(Comparator.comparing(PlaceholderResult::name))
            .distinct()
            .toList();

        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

        // Generate combined documentation
        DocumentationResult combinedDocs = new DocumentationResult(
            commandResults,
            permissionResults,
            placeholderResults
        );

        try (FileWriter fileWriter = new FileWriter("raw_eternalcore_documentation.json")) {
            gson.toJson(combinedDocs, fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        // Generate separate placeholder documentation
        try (FileWriter fileWriter = new FileWriter("raw_eternalcore_placeholders.json")) {
            gson.toJson(placeholderResults, fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public record DocumentationResult(
        List<CommandResult> commands,
        List<PermissionResult> permissions,
        List<PlaceholderResult> placeholders
    ) {}
}
