package com.eternalcode.core.home.command;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.home.Home;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleOrElseArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.suggestion.Suggestion;
import dev.rollczi.litecommands.handle.LiteException;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("name")
public class HomeArgument implements SingleOrElseArgument<CommandSender, ArgHome> {

    private final HomeManager homeManager;
    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public HomeArgument(HomeManager homeManager, BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.homeManager = homeManager;
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<ArgHome> context, String argument) {
        Messages messages = languageManager.getDefaultMessages();
        Home home = userProvider.getUser(invocation)
            .flatMap(user -> homeManager.getHome(user, argument))
            .orThrow(() -> new LiteException(messages.home().notExist()));

        return MatchResult.matchedSingle(home);
    }

    @Override
    public MatchResult orElse(LiteInvocation invocation, ArgumentContext<ArgHome> context) {
        return this.match(invocation, context, "home");
    }

    @Override
    public List<Suggestion> suggestion(LiteInvocation invocation, Parameter parameter, ArgHome annotation) {
        return userProvider.getUser(invocation)
            .map(user -> homeManager.getHomes(user.getUniqueId()))
            .orElseGet(Collections::emptyList)
            .stream()
            .map(Home::getName)
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

}
