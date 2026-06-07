package com.eternalcode.core.configuration.transformer;

import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.SerdesContext;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class MaterialTransformerTest {

    private final MaterialTransformer transformer = new MaterialTransformer();

    @Test
    void shouldSerializeMaterialToName() {
        String serialized = this.transformer.leftToRight(Material.STONE, dummyContext());

        assertEquals("STONE", serialized);
    }

    @Test
    void shouldDeserializeMaterialWithXSeriesAlias() {
        Material material = this.transformer.rightToLeft("WEB", dummyContext());

        assertEquals(Material.COBWEB, material);
    }

    @Test
    void shouldUseFallbackWhenMaterialIsUnsupported() {
        Material material = this.transformer.rightToLeft("UNKNOWN_MATERIAL", dummyContext());

        assertEquals(Material.STONE, material);
    }

    private static SerdesContext dummyContext() {
        Configurer dummyConfigurer = mock(Configurer.class);
        return SerdesContext.of(dummyConfigurer);
    }
}
