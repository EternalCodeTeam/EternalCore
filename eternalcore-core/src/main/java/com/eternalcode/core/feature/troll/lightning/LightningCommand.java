package com.eternalcode.core.feature.troll.lightning;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Command(name = "lightning", aliases = { "strike" })
@Permission("eternalcore.lightning")
public class LightningCommand {

    private final PluginConfiguration config;

    @Inject
    public LightningCommand(PluginConfiguration config) {
        this.config = config;
    }

    @Execute
    void execute(@Context Player player) {
        Block block = player.getTargetBlockExact(this.config.fun.maxLightningBlockDistance);
        if (block == null) {
            if (this.config.fun.lightningStrikePlayerIfNoBlock) {
                player.getWorld().strikeLightning(player.getLocation());
                return;
            }
            return;
        }
        block.getWorld().strikeLightning(block.getLocation());
    }
}
