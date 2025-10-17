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
import java.util.List;
import static java.util.Comparator.comparing;

public class GenerateDocs {

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create();

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("com.eternalcode.core.EternalCore");
        EternalScanner scanner = new EternalScanner(aClass.getClassLoader(), aClass.getPackage());

        List<CommandResult> commandResults = scanner.scan(new CommandScanResolver(), comparing(CommandResult::name));
        List<PermissionResult> permissionResults = scanner.scan(new PermissionScanResolver(), comparing(PermissionResult::name));
        List<PlaceholderResult> placeholderResults = scanner.scan(new PlaceholderScanResolver(), comparing(PlaceholderResult::name));

        generateResult("raw_eternalcore_documentation.json", new DocumentationResult(commandResults, permissionResults));
        generateResult("raw_eternalcore_placeholders.json", placeholderResults);
    }

    private static void generateResult(String fileName, Object objectToRender) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            GSON.toJson(objectToRender, fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public record DocumentationResult(
        List<CommandResult> commands,
        List<PermissionResult> permissions
    ) {}
}
