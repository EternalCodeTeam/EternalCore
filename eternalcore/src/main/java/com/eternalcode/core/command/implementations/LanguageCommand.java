package com.eternalcode.core.command.implementations;

import com.eternalcode.core.inventory.LanguageInventory;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "language", aliases = "lang")
@Permission("eternalcore.command.language")
public class LanguageCommand {

    private final LanguageInventory languageInventory;

    public LanguageCommand(LanguageInventory languageInventory) {
        this.languageInventory = languageInventory;
    }

    @Execute
    public void execute(Player player) {
        this.languageInventory.open(player);
    }
}
