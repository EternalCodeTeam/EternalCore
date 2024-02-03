package com.eternalcode.core.feature.catboy;

import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "catboy")
@Permission("eternalcore.catboy")
class CatboyCommand {

    private final CatboyService catboyService;

    @Inject
    CatboyCommand(CatboyService catboyService) {
        this.catboyService = catboyService;
    }

    @Execute
    void executeCatboy(@Context Player player) {
        if (this.catboyService.isCatboy(player.getUniqueId())) {
            this.catboyService.unmarkAsCatboy(player);
            return;
        }

        this.catboyService.markAsCatboy(player);
    }

}
