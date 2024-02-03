package com.eternalcode.core.feature.catboy;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "catboy")
@Permission("eternalcore.catboy")
class CatboyCommand {

    private final CatboyService catboyService;

    @Inject
    CatboyCommand(CatboyService catboyService) {
        this.catboyService = catboyService;
    }

    @Execute
    @DescriptionDocs(description = "Mark yourself as a catboy.", arguments = "[type]")
    void executeCatboySelf(@Context Player player, @Arg("type") Optional<Cat.Type> type) {
        if (this.catboyService.isCatboy(player.getUniqueId())) {
            this.catboyService.unmarkAsCatboy(player);
            return;
        }

        this.catboyService.markAsCatboy(player, type.orElse(Cat.Type.BLACK));
    }

    @Execute
    @DescriptionDocs(description = "Mark a player as a catboy.", arguments = "<player> [type]")
    @Permission("eternalcore.catboy.others")
    void executeCatboy(@Arg Player target, @Arg("type") Optional<Cat.Type> type) {
        if (this.catboyService.isCatboy(target.getUniqueId())) {
            this.catboyService.unmarkAsCatboy(target);
            return;
        }

        this.catboyService.markAsCatboy(target, type.orElse(Cat.Type.BLACK));
    }

}
