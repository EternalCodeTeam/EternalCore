package com.eternalcode.core.configuration.composer;

import com.eternalcode.core.position.Position;
import org.junit.jupiter.api.Test;
import panda.std.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionComposerTest {

    PositionComposer composer = new PositionComposer();

    @Test
    void testDeserialize() {
        String source = "Position{x=1.0, y=2.0, z=3.0, yaw=4.0, pitch=5.0, world='world'}";
        Result<Position, Exception> result = this.composer.deserialize(source);

        assertTrue(result.isOk());
        Position position = result.get();

        assertEquals(1.0, position.getX());
        assertEquals(2.0, position.getY());
        assertEquals(3.0, position.getZ());
        assertEquals(4.0, position.getYaw());
        assertEquals(5.0, position.getPitch());
        assertEquals("world", position.getWorld());
    }

    @Test
    void testSerialize() {
        Position position = new Position(1.0, 2.0, 3.0, 4.0f, 5.0f, "world");
        Result<String, Exception> result = this.composer.serialize(position);

        String serialized = result.get();
        assertTrue(result.isOk());
        assertEquals("Position{x=1.0, y=2.0, z=3.0, yaw=4.0, pitch=5.0, world='world'}", serialized);
    }

}
