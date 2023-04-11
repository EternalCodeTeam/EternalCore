package com.eternalcode.annotations.scan;

import com.eternalcode.annotations.scan.command.CommandResult;
import com.eternalcode.annotations.scan.command.CommandScanResolver;
import com.eternalcode.annotations.scan.feature.FeatureResult;
import com.eternalcode.annotations.scan.feature.FeatureScanResolver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EternalScanner scanner = new EternalScanner(Main.class.getClassLoader(), Main.class.getPackage());

        List<FeatureResult> featureResults = scanner.scan(new FeatureScanResolver());
        List<CommandResult> commandResults = scanner.scan(new CommandScanResolver());

        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

        try (FileWriter fileWriter = new FileWriter("raw_features_docs.json")) {
            gson.toJson(featureResults, fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

        try (FileWriter fileWriter = new FileWriter("raw_commands_docs.json")) {
            gson.toJson(commandResults, fileWriter);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
