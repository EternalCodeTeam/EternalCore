package com.eternalcode.core.command.implementation;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;

@Section(route = "back")
public class BackCommand { //TODO: Zapisywać w teleportService loc ostatniej teleportacji

    @Execute
    public void execute() {

    }

}
