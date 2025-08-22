package com.eternalcode.example.feature.jail;

import com.eternalcode.core.feature.jail.JailService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;

@Command(name = "api jail")
public class ApiJailCommand {

    private final JailService jailService;

    public ApiJailCommand(JailService jailService) {
        this.jailService = jailService;
    }

    /**
     * This method allows jailed player to buy freedom for 1 exp.
     */
    @Execute(name = "buy freedom")
    void executeBuyFreedom(@Sender Player player) {
        if (!this.jailService.isPlayerJailed(player.getUniqueId())) {
            player.sendMessage("You are not jailed!");
            return;
        }

        float exp = player.getExp();

        if (exp < 1F) {
            player.sendMessage("You need at least 1 exp to buy freedom!");
            return;
        }

        exp -= 1F;
        player.setExp(exp);

        this.jailService.releasePlayer(player);
    }
}
