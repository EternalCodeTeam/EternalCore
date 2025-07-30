package com.eternalcode.core.feature.lightning;

import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Command(name = "lightning", aliases = { "strike" })
@Permission("eternalcore.lightning")
public class LightningCommand {

    private final LightningSettings settings;

    @Inject
    public LightningCommand(LightningSettings settings) {
        this.settings = settings;
    }

    @Execute
    void execute(@Sender Player player) {
        Block block = player.getTargetBlockExact(this.settings.maxLightningBlockDistance());
        if (block == null) {
            if (this.settings.lightningStrikePlayerIfNoBlock()) {
                player.getWorld().strikeLightning(player.getLocation());
                return;
            }
            return;
        }
        block.getWorld().strikeLightning(block.getLocation());
    }

    @Execute
    void player(@Sender Player sender, @Arg Player target) {
        target.getWorld().strikeLightning(sender.getLocation());
    }
}
