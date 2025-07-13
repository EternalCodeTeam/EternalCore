package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "vanish", aliases = {"v"})
@Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION)
public class VanishCommand {

    private final VanishService vanishService;

    @Inject
    public VanishCommand(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @Execute
    void vanishSelf(@Context Player player) {
        if (this.vanishService.isVanished(player)) {
            this.vanishService.disableVanish(player);
            player.sendMessage("You are no longer vanished.");
            return;
        }

        this.vanishService.enableVanish(player);
        player.sendMessage("You are now vanished.");
    }

    @Execute
    @Permission(VanishPermissionConstant.VANISH_COMMAND_PERMISSION_OTHER)
    void vanishOther(@Context Player player, @Arg Player target) {
        if (this.vanishService.isVanished(target)) {
            this.vanishService.disableVanish(target);
            player.sendMessage("You have unvanished " + target.getName() + ".");
            return;
        }

        this.vanishService.enableVanish(target);
        player.sendMessage("You have vanished " + target.getName() + ".");
    }

}
