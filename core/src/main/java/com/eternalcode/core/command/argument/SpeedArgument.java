package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Result;

import java.util.List;
import java.util.stream.IntStream;

@ArgumentName("speed")
public class SpeedArgument implements OneArgument<Integer> {

    @Override
    public Result<Integer, ?> parse(LiteInvocation invocation, String argument) {
        return Result.ok(Integer.parseInt(argument));
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return IntStream.range(0, 11)
            .mapToObj(String::valueOf)
            .map(Suggestion::of)
            .toList();
    }
}
