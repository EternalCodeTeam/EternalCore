package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.gui.KitGuiLayout;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin slot")
@Permission("eternalcore.kits.admin.slot")
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
            this.notices.send(sender, KitMessages::invalidSlot, new Formatter().register(KitNotices.MAX, maxSlot));
            return;
        }

        this.saver.save(sender, kit.toBuilder().slot(slot).build(), KitMessages::slotChanged, new Formatter().register(KitNotices.SLOT, slot));
    }
}
