package com.eternalcode.core.command.implementations;

import com.eternalcode.core.teleport.TeleportRequest;
import com.eternalcode.core.teleport.TeleportRequestManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

import java.util.List;

@Section(route = "tpa")
@Permission("eternalcore.command.tpa")
public class TpaCommand {

    private final TeleportRequestManager teleportRequestManager;

    public TpaCommand(TeleportRequestManager teleportRequestManager) {
        this.teleportRequestManager = teleportRequestManager;
    }

    @Execute
    public void execute(Player player, Player target) {
    }
}
