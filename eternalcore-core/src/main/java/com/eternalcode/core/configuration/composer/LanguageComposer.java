package com.eternalcode.core.configuration.composer;

import com.eternalcode.core.translation.Language;
import panda.std.Result;
import panda.utilities.text.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageComposer implements SimpleComposer<Language> {

    @Override
    public Result<Language, Exception> deserialize(String source) {
        List<String> arguments = Arrays.asList(source.split("\\|"));

        return Result.ok(new Language(arguments.getFirst(), arguments.subList(1, arguments.size())));
    }

    @Override
    public Result<String, Exception> serialize(Language entity) {
        List<String> all = new ArrayList<>();

        all.add(entity.getLang());
        all.addAll(entity.getAliases());

        return Result.ok(Joiner.on("|").join(all).toString());
    }
}
