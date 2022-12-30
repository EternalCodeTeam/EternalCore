package com.eternalcode.core.configuration.language;

import org.junit.jupiter.api.Test;
import panda.std.Result;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocaleComposerTest {

    LocaleComposer composer = new LocaleComposer();

    @Test
    public void testDeserialize() {
        String source = "en";
        Result<Locale, Exception> result = this.composer.deserialize(source);

        assertTrue(result.isOk());
        assertEquals("en", result.get().getLanguage());
    }

    @Test
    public void testSerialize() {
        Locale entity = new Locale("en");
        Result<String, Exception> result = this.composer.serialize(entity);

        assertTrue(result.isOk());
        assertEquals("en", result.get());
    }
}
