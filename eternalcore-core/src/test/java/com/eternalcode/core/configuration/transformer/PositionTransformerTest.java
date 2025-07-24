package com.eternalcode.core.configuration.transformer;

import com.eternalcode.commons.bukkit.position.Position;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.SerdesContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionTransformerTest {

    private final PositionTransformer transformer = new PositionTransformer();

    private SerdesContext dummyContext() {
        Configurer dummyConfigurer = mock(Configurer.class);
        return SerdesContext.of(dummyConfigurer);
    }

    @Test
    void testLeftToRightAndBack() {
        Position original = new Position(12.34, 64.0, -56.78, 90.0f, 45.0f, "world");

        String serialized = transformer.leftToRight(original, dummyContext());
        Position deserialized = transformer.rightToLeft(serialized, dummyContext());

        assertEquals(original.x(), deserialized.x(), 0.0001);
        assertEquals(original.y(), deserialized.y(), 0.0001);
        assertEquals(original.z(), deserialized.z(), 0.0001);
        assertEquals(original.yaw(), deserialized.yaw(), 0.0001f);
        assertEquals(original.pitch(), deserialized.pitch(), 0.0001f);
        assertEquals(original.world(), deserialized.world());
    }

    @Test
    void testRightToLeftInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            transformer.rightToLeft("invalid string", dummyContext());
        });
    }

    @Test
    void testNoneWorldDetection() {
        Position noneWorldPosition = new Position(0.0, 0.0, 0.0, 0.0f, 0.0f, Position.NONE_WORLD);
        assertTrue(noneWorldPosition.isNoneWorld());
    }
}
