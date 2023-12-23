package com.eternalcode.core.feature.language;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "language", aliases = { "lang" })
@Permission("eternalcore.language")
class LanguageCommand {

    private final LanguageInventory languageInventory;

    @Inject
    LanguageCommand(LanguageInventory languageInventory) {
        this.languageInventory = languageInventory;
    }

    @Execute
    @DescriptionDocs(description = "Open language inventory")
    void execute(@Context Player player, @Context User user) {
        this.languageInventory.open(player, user.getLanguage());
    }

}
