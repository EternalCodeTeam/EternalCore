package com.eternalcode.core.translation;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class TranslationConfig extends OkaeriConfig implements TranslationSettings {

    @Comment({
        "# Server Language Configuration",
        "# Choose the language that will be used across the entire server",
        "# Available options: EN (English), PL (Polish)",
    })
    public Language language = Language.EN;
}
