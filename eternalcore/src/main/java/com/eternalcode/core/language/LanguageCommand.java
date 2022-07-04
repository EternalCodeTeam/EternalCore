package com.eternalcode.core.language;

import com.eternalcode.core.language.LanguageInventory;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "language", aliases = { "lang" })
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
