package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.MultilevelArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ArgumentName("x y z")
public class LocationArgument implements MultilevelArgument<Location> {

    @Override
    public Result<Location, String> parseMultilevel(LiteInvocation invocation, String... arguments) {
        return Result.supplyThrowing(NumberFormatException.class, () -> {
            double x = Double.parseDouble(arguments[0]);
            double y = Double.parseDouble(arguments[1]);
            double z = Double.parseDouble(arguments[2]);

            return new Location(null, x, y, z);
        }).mapErr(ex -> "&cNie poprawna lokalizacja!"); //TODO: language
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        List<Suggestion> suggestions = Arrays.asList(
            Suggestion.multilevel("100", "100", "100"),
            Suggestion.multilevel("5", "5", "5"),
            Suggestion.multilevel("10", "35", "-10")
        );

        if (invocation.sender().getHandle() instanceof Player player) {
            Location location = player.getLocation();

            suggestions = new ArrayList<>(suggestions);
            suggestions.add(Suggestion.multilevel(String.valueOf(location.getBlockX()), String.valueOf(location.getBlockY()), String.valueOf(location.getBlockZ())));
        }

        return suggestions;
    }

    @Override
    public boolean validate(LiteInvocation invocation, Suggestion suggestion) {
        for (String suggest : suggestion.multilevelList()) {
            if (!suggest.matches("-?[\\d.]+")) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int countMultilevel() {
        return 3;
    }

}
