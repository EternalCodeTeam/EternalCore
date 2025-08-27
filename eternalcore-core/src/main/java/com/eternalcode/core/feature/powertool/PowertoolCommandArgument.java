package com.eternalcode.core.feature.powertool;

import static com.eternalcode.core.feature.powertool.PowertoolCommandArgument.KEY;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.EternalCoreBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.shared.Lazy;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

@LiteArgument(type = String.class, name = KEY)
class PowertoolCommandArgument extends ArgumentResolver<CommandSender, String> {

    public static final String KEY = "powertool_command";

    private final NoticeService noticeService;
    private final Server server;
    private final Lazy<Map<String, Command>> knownCommands;

    @Inject
    PowertoolCommandArgument(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
        this.knownCommands = new Lazy<>(this::fetchKnownCommands);
    }

    @Override
    protected ParseResult<String> parse(
        Invocation<CommandSender> invocation,
        Argument<String> context,
        String argument
    ) {
        String[] parts = argument.trim().split("\\s+");
        String mainCommand = parts[0];

        Map<String, Command> knownCommands = this.knownCommands.get();

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
        Map<String, Command> knownCommands = this.knownCommands.get();
        return SuggestionResult.of(knownCommands.keySet());
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

                return knownCommands.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
        }
        catch (IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
        throw new IllegalStateException("CommandMap is not instance of SimpleCommandMap"); // should never happen
    }
}
