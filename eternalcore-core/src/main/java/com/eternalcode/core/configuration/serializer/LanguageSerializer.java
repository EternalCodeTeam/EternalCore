package com.eternalcode.core.configuration.serializer;

import com.eternalcode.core.translation.Language;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

public class LanguageSerializer implements ObjectSerializer<Language> {

    @Override
    public boolean supports(Class<? super Language> type) {
        return Language.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Language language, SerializationData data, GenericsDeclaration generics) {
        data.setValue(language.name());
    }

    @Override
    public Language deserialize(DeserializationData data, GenericsDeclaration generics) {
        String value = data.getValue(String.class);

        try {
            return Language.valueOf(value.toUpperCase());
        }
        catch (IllegalArgumentException exception) {
            return Language.EN;
        }
    }
}
