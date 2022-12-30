package com.eternalcode.core.command.implementation.inventory;

import com.eternalcode.core.util.legacy.Legacy;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.user.UserManager;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Route(name = "disposal")
@Permission("eternalcore.disposal")
public class DisposalCommand {

    private final MiniMessage miniMessage;
    private final LanguageManager languageManager;
    private final UserManager userManager;
    private final Server server;

    public DisposalCommand(MiniMessage miniMessage, LanguageManager languageManager, UserManager userManager, Server server) {
        this.miniMessage = miniMessage;
        this.languageManager = languageManager;
        this.userManager = userManager;
        this.server = server;
    }

    @Execute
    void execute(Player player) {
        Language language = this.userManager.getUser(player.getUniqueId())
            .map(user -> user.getSettings().getLanguage())
            .orElseGet(Language.DEFAULT);

        Messages messages = this.languageManager.getMessages(language);
        Component component = this.miniMessage.deserialize(messages.other().disposalTitle());
        String serialize = Legacy.SECTION_SERIALIZER.serialize(component);

        player.openInventory(this.server.createInventory(null, 54, serialize));
    }

}
