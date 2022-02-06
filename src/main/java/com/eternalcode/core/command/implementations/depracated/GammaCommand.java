/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations.depracated;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@FunnyComponent
public class GammaCommand {
    private final ConfigurationManager configurationManager;

    public GammaCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "gamma",
        aliases = {"nightvision, fullbright"},
        permission = "eternalcore.command.nightvision",
        usage = "&cPoprawne użycie &7/gamma",
        acceptsExceeded = true
    )

    public void execute(Player player) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();

        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(ChatUtils.color(config.gammaDisable));
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999, 0, false));
        player.sendMessage(ChatUtils.color(config.gammaEnabled));
    }
}

