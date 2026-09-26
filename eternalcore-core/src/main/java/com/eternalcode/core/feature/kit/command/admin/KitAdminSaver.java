package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.multification.notice.Notice;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.command.CommandSender;

/**
 * Persists an admin change and reports the outcome - shared by every /kitadmin command.
 */
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
        this.save(sender, kit, successMessage, Map.of());
    }

    void save(CommandSender sender, Kit kit, Function<KitMessages, Notice> successMessage, Map<String, String> placeholders) {
        Map<String, String> allPlaceholders = new HashMap<>(placeholders);
        allPlaceholders.put(KitNotices.KIT, kit.name());

        this.kitService.saveKit(kit);
        this.notices.send(sender, successMessage, allPlaceholders);
    }
}
