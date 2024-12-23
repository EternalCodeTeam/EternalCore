package com.eternalcode.core.configuration.composer;

import dev.rollczi.litecommands.reflect.ReflectUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.model.Element;
import net.dzikoysk.cdn.model.Entry;
import net.dzikoysk.cdn.model.Piece;
import net.dzikoysk.cdn.reflect.TargetType;
import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import org.bukkit.util.OldEnum;
import panda.std.Result;

public class OldEnumComposer implements Composer<OldEnum<?>>, SimpleDeserializer<OldEnum<?>> {

    public static final Predicate<Class<?>> IS_OLD_ENUM = type -> OldEnum.class.isAssignableFrom(type);

    private static final Map<Class<?>, Method> VALUE_OF_METHODS = new HashMap<>();

    @Override
    public Result<OldEnum<?>, Exception> deserialize(String source) {
        throw new UnsupportedOperationException("Enum deserializer requires enum class");
    }

    @Override
    public Result<OldEnum<?>, Exception> deserialize(TargetType type, String source) {
        try {
            Method valueOfMethod = VALUE_OF_METHODS.computeIfAbsent(type.getType(), key -> findValueOfMethod(key));
            OldEnum<?> oldEnum = ReflectUtil.invokeStaticMethod(valueOfMethod, source);

            return Result.ok(oldEnum);
        }
        catch (IllegalArgumentException argumentException) {
            return Result.error(argumentException);
        }
    }

    private Method findValueOfMethod(Class<?> type) {
        Method valueOfMethod = ReflectUtil.getMethod(type, "valueOf", String.class);
        valueOfMethod.setAccessible(true);
        return valueOfMethod;
    }

    @Override
    public Result<? extends Element<?>, ? extends Exception> serialize(CdnSettings settings, List<String> description, String key, TargetType type, OldEnum<?> oldEnum) {
        return Result.ok(key.isEmpty() ? new Piece(oldEnum.name()) : new Entry(description, key, oldEnum.name()));
    }

}
