package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.shared.Formatter;
import java.util.function.Function;
import org.bukkit.command.CommandSender;

@Service
class KitAdminSaver {

    private final KitService kitService;
    private final KitNotices notices;

    @Inject
    KitAdminSaver(KitService kitService, KitNotices notices) {
        this.kitService = kitService;
        this.notices = notices;
    }

    void save(CommandSender sender, Kit kit, Function<KitMessages, Notice> successMessage) {
        this.save(sender, kit, successMessage, new Formatter());
    }

    void save(CommandSender sender, Kit kit, Function<KitMessages, Notice> successMessage, Formatter formatter) {
        formatter
            .register(KitNotices.KIT_NAME, kit.name())
            .register(KitNotices.KIT, kit.displayName());

        this.kitService.saveKit(kit);
        this.notices.send(sender, successMessage, formatter);
    }
}
