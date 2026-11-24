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

@Command(name = "kitadmin commands")
@Permission("eternalcore.kits.admin.commands")
class KitCommandsCommand {

    private static final String COMMAND_PREFIX = "/";

    private final KitAdminSaver saver;
    private final KitNotices notices;
    private final TranslationManager translationManager;

    @Inject
    KitCommandsCommand(KitAdminSaver saver, KitNotices notices, TranslationManager translationManager) {
        this.saver = saver;
        this.notices = notices;
        this.translationManager = translationManager;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds console command executed on claim, {PLAYER} = player name", arguments = "<kit> <command>")
    void add(@Sender CommandSender sender, @Arg Kit kit, @Join String command) {
        String normalized = command.startsWith(COMMAND_PREFIX) ? command.substring(COMMAND_PREFIX.length()) : command;

        List<String> commands = new ArrayList<>(kit.commands());
        commands.add(normalized);

        this.saver.save(sender, kit.toBuilder().commands(commands).build(), KitMessages::commandAdded, new Formatter().register(KitNotices.COMMAND, normalized));
    }

    @Execute(name = "list")
    @DescriptionDocs(description = "Shows console commands executed on claim", arguments = "<kit>")
    void list(@Sender CommandSender sender, @Arg Kit kit) {
        List<String> commands = kit.commands();

        if (commands.isEmpty()) {
            this.notices.send(sender, KitMessages::commandsEmpty, new Formatter().register(KitNotices.KIT_NAME, kit.name()));
            return;
        }

        String entryFormat = this.translationManager.getMessages().kit().commandsListEntry();

        Formatter formatter = new Formatter()
            .register(KitNotices.KIT_NAME, kit.name())
            .register("{COMMANDS}", KitAdminListFormatter.format(commands, entryFormat, KitNotices.COMMAND));

        this.notices.send(sender, KitMessages::commandsList, formatter);
    }

    @Execute(name = "remove")
    @DescriptionDocs(description = "Removes console command by its number from /kitadmin commands list", arguments = "<kit> <number>")
    void remove(@Sender CommandSender sender, @Arg Kit kit, @Arg int number) {
        List<String> commands = new ArrayList<>(kit.commands());

        if (commands.isEmpty()) {
            this.notices.send(sender, KitMessages::commandsEmpty, new Formatter().register(KitNotices.KIT_NAME, kit.name()));
            return;
        }

        if (number < 1 || number > commands.size()) {
            Formatter formatter = new Formatter()
                .register(KitNotices.KIT_NAME, kit.name())
                .register(KitNotices.MAX, commands.size());

            this.notices.send(sender, KitMessages::invalidCommandNumber, formatter);
            return;
        }

        String removed = commands.remove(number - 1);

        this.saver.save(sender, kit.toBuilder().commands(commands).build(), KitMessages::commandRemoved, new Formatter().register(KitNotices.COMMAND, removed));
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Removes all kit commands", arguments = "<kit>")
    void clear(@Sender CommandSender sender, @Arg Kit kit) {
        this.saver.save(sender, kit.toBuilder().commands(List.of()).build(), KitMessages::commandsCleared);
    }
}
