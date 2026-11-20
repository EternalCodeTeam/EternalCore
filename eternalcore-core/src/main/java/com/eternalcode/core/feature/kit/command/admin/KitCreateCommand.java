package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.gui.KitGuiLayout;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin create")
@Permission(KitPermissions.ADMIN)
class KitCreateCommand {

    private final KitService kitService;
    private final KitSettings settings;
    private final KitAdminSaver saver;
    private final KitNotices notices;

    @Inject
    KitCreateCommand(KitService kitService, KitSettings settings, KitAdminSaver saver, KitNotices notices) {
        this.kitService = kitService;
        this.settings = settings;
        this.saver = saver;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Creates a kit without cooldown", arguments = "<name>")
    void createWithoutCooldown(@Sender CommandSender sender, @Arg String name) {
        this.create(sender, name, Duration.ZERO);
    }

    @Execute
    @DescriptionDocs(description = "Creates a kit", arguments = "<name> <cooldown>")
    void create(@Sender CommandSender sender, @Arg String name, @Arg Duration cooldown) {
        String kitName = name.toLowerCase(Locale.ROOT);

        if (!Kit.isValidName(kitName)) {
            this.notices.send(sender, KitMessages::invalidName, Map.of(KitNotices.KIT, name));
            return;
        }

        if (this.kitService.exists(kitName)) {
            this.notices.send(sender, KitMessages::alreadyExists, Map.of(KitNotices.KIT, kitName));
            return;
        }

        String permission = this.settings.defaultPermissionPrefix() + kitName;
        Kit kit = Kit.builder(kitName)
            .cooldown(cooldown.isNegative() ? Duration.ZERO : cooldown)
            .permission(permission)
            .slot(this.findFreeSlot())
            .build();

        this.saver.save(sender, kit, KitMessages::created, Map.of(KitNotices.PERMISSION, permission));
    }

    private int findFreeSlot() {
        Set<Integer> usedSlots = this.kitService.getKits().stream()
            .map(Kit::slot)
            .collect(Collectors.toSet());

        int slotCount = KitGuiLayout.slotCount(this.settings.gui().rows());

        for (int slot = Kit.MIN_SLOT; slot < slotCount; slot++) {
            if (!usedSlots.contains(slot)) {
                return slot;
            }
        }

        return Kit.MIN_SLOT;
    }
}
