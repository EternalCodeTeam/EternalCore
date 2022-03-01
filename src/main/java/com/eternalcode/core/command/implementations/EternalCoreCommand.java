/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.configuration.ConfigurationManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@Section(route = "eternalcore", aliases = { "eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials" })
@UsageMessage("&8» &cPoprawne użycie &7/eternalcore <reload>")
@Permission("eternalcore.command.eternalcore")
public class EternalCoreCommand {

    private final AudiencesService audiencesService;
    private final ConfigurationManager manager;
    private final Server server;

    public EternalCoreCommand(AudiencesService audiencesService, ConfigurationManager manager, Server server) {
        this.audiencesService = audiencesService;
        this.manager = manager;
        this.server = server;
    }

    @Execute(route = "reload")
    @Permission("eternalcore.command.reload")
    public void reload(CommandSender sender) {
        this.manager.loadAndRenderConfigs();

        this.audiencesService.sender(sender, messages -> messages.other().successfullyReloaded());

        this.server.getLogger().info("Configs has ben successfuly reloaded!");
    }
}
