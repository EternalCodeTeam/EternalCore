package com.eternalcode.core.configuration.language;

import com.eternalcode.core.language.Language;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LanguageComposerTest {
    LanguageComposer composer = new LanguageComposer();

    @Test
    void testDeserialize() {
        Language expectedLanguage = new Language("en", List.of("en_us", "en_gb"));
        String serializedLanguage = "en|en_us|en_gb";

        Language actualLanguage = this.composer.deserialize(serializedLanguage).get();
        assertEquals(expectedLanguage, actualLanguage);
    }

    @Test
    void testSerialize() {
        Language language = new Language("en", List.of("en_us", "en_gb"));
        String expectedSerializedLanguage = "en|en_us|en_gb";

        String actualSerializedLanguage = this.composer.serialize(language).get();
        assertEquals(expectedSerializedLanguage, actualSerializedLanguage);
    }
}
