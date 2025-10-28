package com.eternalcode.core.placeholder;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;


@Getter
@Accessors(fluent = true)
public class PlaceholdersConfig extends OkaeriConfig implements PlaceholdersSettings {

    @Comment("# Map of available placeholders and their default values")
    public Map<String, String> placeholders = Map.of(
        "prefix", "&7",
        "online_count", "{ONLINE}"
    );

    @Override
    public Map<String, String> placeholders() {
        return placeholders;
    }
}
