package com.eternalcode.core.litecommand.argument;

import dev.rollczi.litecommands.argument.parser.ParseResult;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class ArgumentParser<INPUT, OUTPUT> {

    private final Function<INPUT, ParseResult<OUTPUT>> parser;

    private ArgumentParser(Function<INPUT, ParseResult<OUTPUT>> parser) {
        this.parser = parser;
    }

    public static <INPUT, OUTPUT> ArgumentParser<INPUT, OUTPUT> of() {
        return new ArgumentParser<>(input -> ParseResult.failure(new Object()));
    }

    public ArgumentParser<INPUT, OUTPUT> thenTry(Function<INPUT, ParseResult<OUTPUT>> nextParser) {
        return new ArgumentParser<>(input -> this.parser.apply(input)
            .mapFailure(ignored -> nextParser.apply(input)));
    }

    public ArgumentParser<INPUT, OUTPUT> thenTryIf(
        Predicate<INPUT> condition,
        Function<INPUT, ParseResult<OUTPUT>> nextParser
    ) {
        return new ArgumentParser<>(input -> this.parser.apply(input)
            .mapFailure(ignored -> {
                if (condition.test(input)) {
                    return nextParser.apply(input);
                }
                return ParseResult.failure(new Object());
            }));
    }

    public Function<INPUT, ParseResult<OUTPUT>> build() {
        return this.parser;
    }

    public Function<INPUT, ParseResult<OUTPUT>> buildWithFinalFailure(Function<INPUT, ParseResult<OUTPUT>> finalFailure) {
        return input -> this.parser.apply(input)
            .mapFailure(ignored -> finalFailure.apply(input));
    }

    public Function<INPUT, ParseResult<OUTPUT>> buildWithFinalFailure(Supplier<ParseResult<OUTPUT>> finalFailure) {
        return input -> this.parser.apply(input)
            .mapFailure(ignored -> finalFailure.get());
    }
}

