package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeMutationService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

@Command(name = "delhome")
@Permission("eternalcore.delhome")
class DelHomeCommand {

    private final HomeMutationService homeMutationService;

    @Inject
    DelHomeCommand(HomeMutationService homeMutationService) {
        this.homeMutationService = homeMutationService;
    }

    @Execute
    @DescriptionDocs(description = "Delete home", arguments = "<home>")
    void execute(@Context User user, @Arg Home home) {
        this.homeMutationService.deleteHome(user, home);
    }
}
