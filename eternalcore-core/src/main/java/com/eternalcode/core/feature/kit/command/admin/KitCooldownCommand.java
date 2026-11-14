package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.Map;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin cooldown")
@Permission(KitPermissions.ADMIN)
class KitCooldownCommand {

    private final KitAdminSaver saver;

    @Inject
    KitCooldownCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute
    @DescriptionDocs(description = "Changes kit cooldown (0s = no cooldown)", arguments = "<kit> <cooldown>")
    void setCooldown(@Sender CommandSender sender, @Arg Kit kit, @Arg Duration cooldown) {
        Duration safeCooldown = cooldown.isNegative() ? Duration.ZERO : cooldown;
        Kit updated = kit.toBuilder().cooldown(safeCooldown).build();

        this.saver.save(sender, updated, KitMessages::cooldownChanged, Map.of(
            KitNotices.COOLDOWN, DurationUtil.format(safeCooldown, true)
        ));
    }
}
