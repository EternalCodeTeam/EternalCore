package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.util.StringUtil;
import java.lang.reflect.Field;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;

@Service
public class CustomCommandRegistry {

    private static final String FALLBACK_PREFIX = "eternalcore";
    private static final String EMPTY_USAGE_MESSAGE = StringUtil.EMPTY;

    private final CustomCommandConfig customCommandConfig;
    private final NoticeService noticeService;
    private final Server server;

    private CommandMap commandMap;

    @Inject
    public CustomCommandRegistry(CustomCommandConfig customCommandConfig, NoticeService noticeService, Server server) {
        this.customCommandConfig = customCommandConfig;
        this.noticeService = noticeService;
        this.server = server;

        this.registerCustomCommands();
    }

    public void registerCustomCommands() {
        for (CustomCommand customCommand : this.customCommandConfig.commands) {
            this.registerCustomCommand(customCommand);
        }
    }

    private void registerCustomCommand(CustomCommand customCommand) {
        CustomCommandBukkitWrapper customCommandBukkitWrapper = new CustomCommandBukkitWrapper(
            customCommand.getName(),
            EMPTY_USAGE_MESSAGE,
            customCommand.getAliases(),
            this.noticeService,
            customCommand.getMessage()
        );

        this.commandMap().register(FALLBACK_PREFIX, customCommandBukkitWrapper);
    }

    CommandMap commandMap() {
        if (this.commandMap == null) {
            try {
                Field commandMapField = this.server.getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);

                this.commandMap = (CommandMap) commandMapField.get(this.server);
            }
            catch (NoSuchFieldException | IllegalAccessException exception) {
                throw new RuntimeException(
                    "Failed to get CommandMap from the server, this might be due to a server version incompatibility.",
                    exception);
            }
        }

        return this.commandMap;
    }
}
