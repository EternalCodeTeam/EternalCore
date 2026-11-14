package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.gui.KitItemsEditor;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "kitadmin items")
@Permission(KitPermissions.ADMIN)
class KitItemsCommand {

    private final KitItemsEditor itemsEditor;

    @Inject
    KitItemsCommand(KitItemsEditor itemsEditor) {
        this.itemsEditor = itemsEditor;
    }

    @Execute
    @DescriptionDocs(description = "Opens kit items editor, items are saved on close", arguments = "<kit>")
    void edit(@Sender Player player, @Arg Kit kit) {
        this.itemsEditor.open(player, kit);
    }
}
