package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.multification.shared.Formatter;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin description")
@Permission("eternalcore.kits.admin.description")
class KitDescriptionCommand {

    private final KitAdminSaver saver;
    private final KitNotices notices;
    private final TranslationManager translationManager;

    @Inject
    KitDescriptionCommand(KitAdminSaver saver, KitNotices notices, TranslationManager translationManager) {
        this.saver = saver;
        this.notices = notices;
        this.translationManager = translationManager;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds description line (MiniMessage) shown in the GUI and in /kit info", arguments = "<kit> <line>")
    void add(@Sender CommandSender sender, @Arg Kit kit, @Join String line) {
        List<String> description = new ArrayList<>(kit.description());
        description.add(line);

        this.saver.save(sender, kit.toBuilder().description(description).build(), KitMessages::descriptionLineAdded, new Formatter().register(KitNotices.LINE, line));
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "Shows kit description lines", arguments = "<kit>")
    void list(@Sender CommandSender sender, @Arg Kit kit) {
        List<String> description = kit.description();

        if (description.isEmpty()) {
            this.sendEmpty(sender, kit);
            return;
        }

        String entryFormat = this.translationManager.getMessages().kit().descriptionListEntry();

        Formatter formatter = new Formatter()
            .register(KitNotices.KIT_NAME, kit.name())
            .register("{LINES}", KitAdminListFormatter.format(description, entryFormat, KitNotices.LINE));

        this.notices.send(sender, KitMessages::descriptionList, formatter);
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes description line by its number from /kitadmin description list", arguments = "<kit> <number>")
    void remove(@Sender CommandSender sender, @Arg Kit kit, @Arg int number) {
        List<String> description = new ArrayList<>(kit.description());

        if (description.isEmpty()) {
            this.sendEmpty(sender, kit);
            return;
        }

        if (number < 1 || number > description.size()) {
            Formatter formatter = new Formatter()
                .register(KitNotices.KIT_NAME, kit.name())
                .register(KitNotices.MAX, description.size());

            this.notices.send(sender, KitMessages::invalidDescriptionLineNumber, formatter);
            return;
        }

        String removed = description.remove(number - 1);

        this.saver.save(sender, kit.toBuilder().description(description).build(), KitMessages::descriptionLineRemoved, new Formatter().register(KitNotices.LINE, removed));
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Removes all kit description lines", arguments = "<kit>")
    void clear(@Sender CommandSender sender, @Arg Kit kit) {
        this.saver.save(sender, kit.toBuilder().description(List.of()).build(), KitMessages::descriptionCleared);
    }

    private void sendEmpty(CommandSender sender, Kit kit) {
        this.notices.send(sender, KitMessages::descriptionEmpty, new Formatter().register(KitNotices.KIT_NAME, kit.name()));
    }
}
