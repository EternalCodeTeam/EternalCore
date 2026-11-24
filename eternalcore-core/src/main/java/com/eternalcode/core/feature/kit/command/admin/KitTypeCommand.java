package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin type")
@Permission("eternalcore.kits.admin.type")
class KitTypeCommand {

    private final KitAdminSaver saver;

    @Inject
    KitTypeCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute
    @DescriptionDocs(description = "Changes kit type: RECURRING, ONE_TIME or FIRST_JOIN", arguments = "<kit> <type>")
    void setType(@Sender CommandSender sender, @Arg Kit kit, @Arg KitType type) {
        this.saver.save(sender, kit.toBuilder().type(type).build(), KitMessages::typeChanged, new Formatter().register(KitNotices.TYPE, type.name()));
    }
}
