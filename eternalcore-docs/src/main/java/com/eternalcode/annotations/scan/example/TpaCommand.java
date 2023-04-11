package com.eternalcode.annotations.scan.example;

import com.eternalcode.annotations.scan.command.Description;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;

@Route(name = "tpa", aliases = {"teleporta"})
@Permission("eternalcore.tpa")
public class TpaCommand {

    @Execute
    @Permission("eternalcore.tpa.send")
    @Description("Sends tpa request")
    public void send(@Arg String player) {}

    @Execute(route = "accept")
    @Permission("eternalcore.tpa.accept")
    @Description("Accepts tpa request")
    public void accept() {}


    @Execute(route = "deny")
    @Permission("eternalcore.tpa.deny")
    @Description("Denies tpa request")
    public void deny() {}

}
