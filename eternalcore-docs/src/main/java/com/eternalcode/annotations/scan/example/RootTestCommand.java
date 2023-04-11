package com.eternalcode.annotations.scan.example;

import com.eternalcode.annotations.scan.command.Description;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.root.RootRoute;

@RootRoute
@Permission("permissions.root")
@Permission("permissions.root2")
public class RootTestCommand {

    @Execute(route = "test-root")
    @Permission("permissions.root.execute")
    @Description("This is descriptions")
    public void execute() {}


    @Execute(route = "test-root other")
    @Permission("permissions.root.execute.other")
    @Description("This is descriptions other")
    public void executeOther() {}

}
