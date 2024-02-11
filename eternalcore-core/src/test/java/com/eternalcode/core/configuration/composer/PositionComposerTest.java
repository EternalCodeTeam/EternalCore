package com.eternalcode.core.configuration.composer;

import com.eternalcode.commons.shared.bukkit.position.Position;
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

        assertEquals(1.0, position.x());
        assertEquals(2.0, position.y());
        assertEquals(3.0, position.z());
        assertEquals(4.0, position.yaw());
        assertEquals(5.0, position.pitch());
        assertEquals("world", position.world());
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
