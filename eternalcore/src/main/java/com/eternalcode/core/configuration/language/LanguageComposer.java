package com.eternalcode.core.configuration.language;

import com.eternalcode.core.configuration.composers.SimpleComposer;
import com.eternalcode.core.language.Language;
import panda.std.Result;

public class LanguageComposer implements SimpleComposer<Language> {

    @Override
    public Result<Language, Exception> deserialize(String source) {
        return Result.ok(new Language(source));
    }

    @Override
    public Result<String, Exception> serialize(Language entity) {
        return Result.ok(entity.getLang());
    }
}
