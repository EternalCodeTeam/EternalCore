package com.eternalcode.core.configuration.serializer;

import com.eternalcode.core.feature.language.Language;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

import java.util.Arrays;
import java.util.List;

public class LanguageSerializer implements ObjectSerializer<Language> {

    @Override
    public boolean supports(Class<? super Language> type) {
        return Language.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Language language, SerializationData data, GenericsDeclaration generics) {
        StringBuilder sb = new StringBuilder(language.getLang());
        for (String alias : language.getAliases()) {
            sb.append("|").append(alias);
        }
        data.setValue(sb.toString());
    }

    @Override
    public Language deserialize(DeserializationData data, GenericsDeclaration generics) {
        String value = data.getValue(String.class);
        String[] parts = value.split("\\|");
        
        if (parts.length == 0) {
            throw new IllegalArgumentException("Invalid language format: " + value);
        }
        
        String lang = parts[0];
        List<String> aliases = Arrays.asList(parts).subList(1, parts.length);
        
        return new Language(lang, aliases);
    }
} 
