package com.eternalcode.core.configuration.migration.impl;

import com.eternalcode.core.configuration.migration.Migration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class M2 implements Migration {

    @Override
    public int migrationNumber() {
        return 2;
    }

    @Override
    public Path filePath() {
        return Paths.get("C:", "Users", "vlucky", "Documents", "GitHub", "EternalCore", "config.yml");
    }

    @Override
    public String oldValue() {
        return "test2";
    }

    @Override
    public String newValue() {
        return "test3";
    }

}
