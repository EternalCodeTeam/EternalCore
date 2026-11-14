package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.gui.KitGuiLayout;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Map;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin slot")
@Permission(KitPermissions.ADMIN)
class KitSlotCommand {

    private final KitAdminSaver saver;
    private final KitSettings settings;
    private final KitNotices notices;

    @Inject
    KitSlotCommand(KitAdminSaver saver, KitSettings settings, KitNotices notices) {
        this.saver = saver;
        this.settings = settings;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Moves the kit to another GUI slot", arguments = "<kit> <slot>")
    void setSlot(@Sender CommandSender sender, @Arg Kit kit, @Arg int slot) {
        int maxSlot = KitGuiLayout.slotCount(this.settings.gui().rows()) - 1;

        if (slot < Kit.MIN_SLOT || slot > maxSlot) {
            this.notices.send(sender, KitMessages::invalidSlot, Map.of(KitNotices.MAX, String.valueOf(maxSlot)));
            return;
        }

        this.saver.save(sender, kit.toBuilder().slot(slot).build(), KitMessages::slotChanged, Map.of(
            KitNotices.SLOT, String.valueOf(slot)
        ));
    }
}
