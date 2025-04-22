package com.eternalcode.core.feature.backpack;

import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "backpack")
public class BackpackCommand {

    private final BackpackService backpackService;

    @Inject
    public BackpackCommand(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    @Execute
    void openBackpack(@Context Player player) {
        UUID uniqueId = player.getUniqueId();
        this.backpackService.openBackpack(player, 1);
    }

    @Execute
    void openBackpack(@Context Player player, @Arg Integer slot) {
        UUID uniqueId = player.getUniqueId();
        this.backpackService.openBackpack(player, slot);
    }

}
