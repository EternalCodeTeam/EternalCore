package com.eternalcode.core.feature.fun.catboy;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.catboy.CatboyService;
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
        this.switchCatboy(player, type);
    }

    @Execute
    @DescriptionDocs(description = "Mark a player as a catboy.", arguments = "<player> [type]")
    @Permission("eternalcore.catboy.others")
    void executeCatboy(@Arg("target") Player target, @Arg("type") Optional<Cat.Type> type) {
        this.switchCatboy(target, type);
    }

    private void switchCatboy(Player target, Optional<Cat.Type> type) {
        if (this.catboyService.isCatboy(target.getUniqueId())) {
            if (type.isPresent()) {
                this.catboyService.changeCatboyType(target, type.get());
                return;
            }

            this.catboyService.unmarkAsCatboy(target);
            return;
        }

        this.catboyService.markAsCatboy(target, type.orElse(Cat.Type.BLACK));
    }

}
