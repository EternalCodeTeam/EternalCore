package com.eternalcode.core.configuration.language;

import com.eternalcode.core.configuration.composers.SimpleComposer;
import panda.std.Result;

import java.util.Locale;

public class LocaleComposer implements SimpleComposer<Locale> {

    @Override
    public Result<Locale, Exception> deserialize(String source) {
        return Result.ok(new Locale(source));
    }

    @Override
    public Result<String, Exception> serialize(Locale entity) {
        return Result.ok(entity.getLanguage());
    }
}
