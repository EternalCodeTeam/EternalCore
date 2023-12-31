package com.eternalcode.core.feature.essentials.sudo;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Command(name = "sudo")
public class SudoCommand {

    private final Server server;

    @Inject
    public SudoCommand(Server server) {
        this.server = server;
    }

    @Execute(name = "console")
    @Permission("eternalcore.sudo.console")
    void console(@Context Viewer viewer, @Arg String command) {
        this.server.dispatchCommand(this.server.getConsoleSender(), command);
    }

    @Execute(name = "player")
    @Permission("eternalcore.sudo.player")
    void player(@Context Viewer viewer, @Arg Player target, @Arg String command) {
        this.server.dispatchCommand(target, command);
    }
}
