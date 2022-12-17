package com.eternalcode.core.configuration.composer;

import com.eternalcode.core.shared.Position;
import org.junit.jupiter.api.Test;
import panda.std.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionComposerTest {

    PositionComposer composer = new PositionComposer();

    @Test
    void testDeserialize() {
        String source = "Position{x=1.0, y=2.0, z=3.0, yaw=4.0, pitch=5.0, world='world'}";
        Result<Position, Exception> result = composer.deserialize(source);

        assertTrue(result.isOk());
        Position position = result.get();

        assertEquals(1.0, position.getX(), 0.001);
        assertEquals(2.0, position.getY(), 0.001);
        assertEquals(3.0, position.getZ(), 0.001);
        assertEquals(4.0, position.getYaw(), 0.001);
        assertEquals(5.0, position.getPitch(), 0.001);
        assertEquals("world", position.getWorld());
    }

    @Test
    void testDeserialize_invalidFormat() {
        String source = "Position{x=1.0, y=2.0, z=3.0, yaw=4.0, pitch=5.0, world='world'}";
        Result<Position, Exception> result = composer.deserialize(source);

        assertTrue(result.isOk());
        Position position = result.get();

        assertEquals(1.0, position.getX(), 0.001);
        assertEquals(2.0, position.getY(), 0.001);
        assertEquals(3.0, position.getZ(), 0.001);
        assertEquals(4.0, position.getYaw(), 0.001);
        assertEquals(5.0, position.getPitch(), 0.001);
        assertEquals("world", position.getWorld());
    }

    @Test
    void testSerialize() {
        Position position = new Position(1.0, 2.0, 3.0, 4.0f, 5.0f, "world");
        Result<String, Exception> result = composer.serialize(position);

        String serialized = result.get();
        assertTrue(result.isOk());
        assertEquals("Position{x=1.0, y=2.0, z=3.0, yaw=4.0, pitch=5.0, world='world'}", serialized);
    }

}
