package com.eternalcode.core.feature.language;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "language", aliases = { "lang" })
@Permission("eternalcore.language")
class LanguageCommand {

    private final LanguageInventory languageInventory;

    @Inject
    LanguageCommand(LanguageInventory languageInventory) {
        this.languageInventory = languageInventory;
    }

    @Execute
    @DescriptionDocs(description = "Open language inventory")
    void execute(Player player, User user) {
        this.languageInventory.open(player, user.getLanguage());
    }

}
