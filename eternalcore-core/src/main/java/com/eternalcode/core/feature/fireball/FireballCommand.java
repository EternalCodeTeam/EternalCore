package com.eternalcode.core.feature.fireball;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "fireball")
@Permission("eternalcore.fireball")
public class FireballCommand {

    private static final double DEFAULT_SPEED = 2.0D;

    @Execute
    @DescriptionDocs(description = "Launch a fireball in the direction you are looking", arguments = "[speed]")
    void execute(@Sender Player sender, @Arg Optional<Double> speed) {
        double fireballSpeed = speed.orElse(DEFAULT_SPEED);
        sender.launchProjectile(Fireball.class, sender.getLocation().getDirection().multiply(fireballSpeed));
    }
}
