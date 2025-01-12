package com.eternalcode.core.feature.msgtoggle;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "msgtoggle")
@Permission("eternalcore.msgtoggle")
public class MsgToggleCommand {

    @Execute
    public void execute(@Context Player context) {

    }

    @Execute(name = "on")
    public void on(@Context Player context) {

    }

    @Execute(name = "off")
    public void off(@Context Player context) {

    }

    @Execute
    @Permission("eternalcore.msgtoggle.other")
    public void other(@Context Player context, @Arg("player") String player) {
        context.getSender().sendMessage("You have enabled messages for someone else!");
    }
}
