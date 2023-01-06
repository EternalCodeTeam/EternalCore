package com.eternalcode.core.language;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "language", aliases = { "lang" })
@Permission("eternalcore.language")
public class LanguageCommand {

    private final LanguageInventory languageInventory;

    public LanguageCommand(LanguageInventory languageInventory) {
        this.languageInventory = languageInventory;
    }

    @Execute
    void execute(Player player) {
        this.languageInventory.open(player);
    }

}
