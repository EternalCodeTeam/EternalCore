package com.eternalcode.core.feature.home.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.home.HomeMutationService;
import com.eternalcode.core.feature.home.HomesSettings;
import com.eternalcode.core.feature.home.inventory.HomeInventory;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "sethome")
@Permission("eternalcore.sethome")
class SetHomeCommand {

    private final HomeMutationService homeMutationService;
    private final HomesSettings homesSettings;
    private final HomeInventory homeInventory;

    @Inject
    SetHomeCommand(HomeMutationService homeMutationService, HomesSettings homesSettings, HomeInventory homeInventory) {
        this.homeMutationService = homeMutationService;
        this.homesSettings = homesSettings;
        this.homeInventory = homeInventory;
    }

    @Execute
    @DescriptionDocs(description = "Set home location with specified name", arguments = "<home>")
    void execute(@Sender User user, @Sender Player player, @Arg String home) {
        this.homeMutationService.setOrOverrideHome(user, player, home);
    }

    @Execute
    @DescriptionDocs(description = "Set home location, opens the home GUI instead if it's enabled")
    void execute(@Sender User user, @Sender Player player) {
        if (this.homesSettings.inventoryEnabled()) {
            this.homeInventory.open(player);
            return;
        }

        this.homeMutationService.setOrOverrideHome(user, player, this.homesSettings.defaultName());
    }
}
