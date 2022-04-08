package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.ConfigurationManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;

@Section(route = "eternalcore", aliases = { "eternal" })
@UsageMessage("&8» &cPoprawne użycie &7/eternalcore <reload>")
@Permission("eternalcore.command.eternalcore")
public class EternalCoreCommand {

    private final NoticeService noticeService;
    private final ConfigurationManager manager;
    private final Server server;

    public EternalCoreCommand(NoticeService noticeService, ConfigurationManager manager, Server server) {
        this.noticeService = noticeService;
        this.manager = manager;
        this.server = server;
    }

    @Execute(route = "reload")
    @Permission("eternalcore.command.reload")
    public void reload(Audience audience) {
        this.manager.loadAndRenderConfigs();
        this.noticeService.audience(audience, messages -> messages.other().successfullyReloaded());
        this.server.getLogger().info("Configs has ben successfully reloaded!");
    }
}
