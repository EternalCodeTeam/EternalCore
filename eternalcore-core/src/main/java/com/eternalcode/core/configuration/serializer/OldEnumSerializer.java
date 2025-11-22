package com.eternalcode.core.configuration.serializer;

import dev.rollczi.litecommands.reflect.ReflectUtil;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.util.OldEnum;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class OldEnumSerializer implements ObjectSerializer<OldEnum<?>> {

    private static final Map<Class<?>, Method> VALUE_OF_METHODS = new HashMap<>();

    @Override
    public boolean supports(@NonNull Class<?> type) {
        return OldEnum.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(OldEnum<?> object, SerializationData data, GenericsDeclaration generics) {
        data.setValue(object.name());
    }

    @Override
    public OldEnum<?> deserialize(DeserializationData data, GenericsDeclaration generics) {
        Class<?> enumClass = generics.getType();
        String value = data.getValue(String.class);

        try {
            Method valueOfMethod = VALUE_OF_METHODS.computeIfAbsent(enumClass, this::findValueOfMethod);
            return ReflectUtil.invokeStaticMethod(valueOfMethod, value);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Cannot deserialize OldEnum", exception);
        }
    }

    private Method findValueOfMethod(Class<?> type) {
        Method valueOfMethod = ReflectUtil.getMethod(type, "valueOf", String.class);
        valueOfMethod.setAccessible(true);
        return valueOfMethod;
    }

}
