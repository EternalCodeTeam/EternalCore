package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin cooldown")
@Permission("eternalcore.kits.admin.cooldown")
class KitCooldownCommand {

    private final KitAdminSaver saver;

    @Inject
    KitCooldownCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute
    @DescriptionDocs(description = "Changes cooldown of a recurring kit (0s = no cooldown)", arguments = "<kit> <cooldown>")
    void setCooldown(@Sender CommandSender sender, @Arg Kit kit, @Arg Duration cooldown) {
        Duration safeCooldown = cooldown.isNegative() ? Duration.ZERO : cooldown;
        Kit updated = kit.toBuilder().cooldown(safeCooldown).build();

        this.saver.save(sender, updated, KitMessages::cooldownChanged, new Formatter().register(KitNotices.COOLDOWN, DurationUtil.format(safeCooldown, true)));
    }
}
