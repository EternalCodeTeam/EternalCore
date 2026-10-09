package com.eternalcode.core.feature.kit.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.gui.KitInventory;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "kit", aliases = { "kits" })
@Permission("eternalcore.kit")
@PermissionDocs(
    name = "Kit Cooldown Bypass",
    permission = KitPermissions.BYPASS_COOLDOWN,
    description = "Allows claiming kits without cooldown"
)
class KitCommand {

    private final KitInventory kitInventory;
    private final KitClaimNotifier claimNotifier;

    @Inject
    KitCommand(KitInventory kitInventory, KitClaimNotifier claimNotifier) {
        this.kitInventory = kitInventory;
        this.claimNotifier = claimNotifier;
    }

    @Execute
    @DescriptionDocs(description = "Opens kit GUI")
    void open(@Sender Player player) {
        this.kitInventory.open(player);
    }

    @Execute
    @DescriptionDocs(description = "Claims the kit", arguments = "<kit>")
    void claim(@Sender Player player, @Arg Kit kit) {
        this.claimNotifier.claimAndNotify(player, kit);
    }
}
