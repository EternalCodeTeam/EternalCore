package com.eternalcode.core.feature.powertool;

import static com.eternalcode.core.feature.powertool.PowertoolCommandArgument.KEY;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.TabCompleter;

@LiteArgument(type = String.class, name = KEY)
class PowertoolCommandArgument extends ArgumentResolver<CommandSender, String> {

    public static final String KEY = "powertool_command";
    private static final String CACHE_KEY = "commands";

    private final NoticeService noticeService;
    private final Server server;
    private final Cache<String, Map<String, Command>> commandCache;

    @Inject
    PowertoolCommandArgument(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
        this.commandCache = Caffeine.newBuilder()
            .maximumSize(1)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
    }

    @Override
    protected ParseResult<String> parse(
        Invocation<CommandSender> invocation,
        Argument<String> context,
        String argument
    ) {
        String[] parts = argument.trim().split("\\s+");
        String mainCommand = parts[0];

        Map<String, Command> knownCommands = this.getAllKnownCommands();

        // Sprawdź czy główna komenda istnieje
        if (!knownCommands.containsKey(mainCommand)) {
            EternalCoreBroadcast<Viewer, Translation, ?> invalidCommandNotice = this.noticeService.create()
                .sender(invocation.sender())
                .notice(messages -> messages.powertool().invalidCommand());

            return ParseResult.failure(invalidCommandNotice);
        }

        return ParseResult.success(argument.trim());
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<String> argument,
        SuggestionContext context
    ) {
        String currentInput = context.getCurrent().multilevel();
        String[] parts = currentInput.split("\\s+");

        Map<String, Command> knownCommands = this.getAllKnownCommands();

        if (parts.length <= 1) {
            // Sugeruj główne komendy
            String partial = parts.length == 1 ? parts[0] : "";
            return knownCommands.keySet().stream()
                .filter(cmd -> cmd.toLowerCase().startsWith(partial.toLowerCase()))
                .collect(SuggestionResult.collector());
        } else {
            // Sugeruj subkomendy
            String mainCommand = parts[0];
            Command command = knownCommands.get(mainCommand);

            if (command != null) {
                List<String> subcommandSuggestions = getSubcommandSuggestions(
                    command,
                    invocation.sender(),
                    Arrays.copyOfRange(parts, 1, parts.length)
                );

                String currentSubcommand = parts[parts.length - 1];
                return subcommandSuggestions.stream()
                    .filter(suggestion -> suggestion.toLowerCase().startsWith(currentSubcommand.toLowerCase()))
                    .map(suggestion -> mainCommand + " " + String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1)) +
                        (parts.length > 2 ? " " : "") + suggestion)
                    .collect(SuggestionResult.collector());
            }
        }

        return SuggestionResult.empty();
    }

    private List<String> getSubcommandSuggestions(Command command, CommandSender sender, String[] args) {
        try {
            // Dla PluginCommand, spróbuj użyć TabCompleter
            if (command instanceof PluginCommand pluginCommand) {
                TabCompleter tabCompleter = pluginCommand.getTabCompleter();
                if (tabCompleter != null) {
                    List<String> completions = tabCompleter.onTabComplete(sender, command, command.getName(), args);
                    if (completions != null && !completions.isEmpty()) {
                        return completions;
                    }
                }
            }

            // Fallback do domyślnej implementacji tab completion
            List<String> completions = command.tabComplete(sender, command.getName(), args);
            if (completions != null) {
                return completions;
            }
        } catch (Exception e) {
            // Ignoruj błędy tab completion
        }

        // Zwróć puste sugestie jeśli nic nie działa
        return List.of();
    }

    private Map<String, Command> getAllKnownCommands() {
        return commandCache.get(CACHE_KEY, key -> this.fetchKnownCommands());
    }

    private Map<String, Command> fetchKnownCommands() {
        try {
            Field commandMapField = this.server.getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(this.server);

            if (commandMap instanceof SimpleCommandMap simpleMap) {
                Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);

                @SuppressWarnings("unchecked")
                Map<String, Command> knownCommands = (Map<String, Command>) knownCommandsField.get(simpleMap);

                return knownCommands.entrySet().stream()
                    .filter(entry -> !isInternalCommand(entry.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        }
        catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        throw new IllegalStateException("CommandMap is not instance of SimpleCommandMap");
    }

    private boolean isInternalCommand(String commandName) {
        // Odfiltruj komendy systemowe/wewnętrzne
        Set<String> internalCommands = Set.of(
            "bukkit", "minecraft", "help", "?", "ver", "version",
            "plugins", "pl", "reload", "rl"
        );
        return internalCommands.contains(commandName.toLowerCase());
    }

    /**
     * Czyści cache komend - użyteczne po przeładowaniu pluginów
     */
    public void clearCache() {
        commandCache.invalidateAll();
    }
}
