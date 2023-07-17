package com.eternalcode.core.configuration.migration.impl;

import com.eternalcode.core.configuration.migration.Migration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class M1 implements Migration {

    @Override
    public int migrationNumber() {
        return 1;
    }

    @Override
    public Path filePath() {
        return Paths.get("C:", "Users", "vlucky", "Documents", "GitHub", "EternalCore", "config.yml");
    }

    @Override
    public String oldValue() {
        return "test";
    }

    @Override
    public String newValue() {
        return "test2";
    }

}
