package com.eternalcode.core.configuration.serializer;

import com.eternalcode.commons.bukkit.position.Position;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

public class PositionSerializer implements ObjectSerializer<Position> {

    @Override
    public boolean supports(Class<? super Position> type) {
        return Position.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Position position, SerializationData data, GenericsDeclaration generics) {
        data.setValue(position.toString());
    }

    @Override
    public Position deserialize(DeserializationData data, GenericsDeclaration generics) {
        return Position.parse(data.getValue(String.class));
    }
}
