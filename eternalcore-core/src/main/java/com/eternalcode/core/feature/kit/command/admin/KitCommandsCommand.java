package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitPermissions;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.command.CommandSender;

@Command(name = "kitadmin commands")
@Permission(KitPermissions.ADMIN)
class KitCommandsCommand {

    private static final String COMMAND_PREFIX = "/";

    private final KitAdminSaver saver;

    @Inject
    KitCommandsCommand(KitAdminSaver saver) {
        this.saver = saver;
    }

    @Execute(name = "add")
    @DescriptionDocs(description = "Adds console command executed on claim, {PLAYER} = player name", arguments = "<kit> <command>")
    void add(@Sender CommandSender sender, @Arg Kit kit, @Join String command) {
        String normalized = command.startsWith(COMMAND_PREFIX) ? command.substring(COMMAND_PREFIX.length()) : command;

        List<String> commands = new ArrayList<>(kit.commands());
        commands.add(normalized);

        this.saver.save(sender, kit.toBuilder().commands(commands).build(), KitMessages::commandAdded, Map.of(
            KitNotices.COMMAND, normalized
        ));
    }

    @Execute(name = "clear")
    @DescriptionDocs(description = "Removes all kit commands", arguments = "<kit>")
    void clear(@Sender CommandSender sender, @Arg Kit kit) {
        this.saver.save(sender, kit.toBuilder().commands(List.of()).build(), KitMessages::commandsCleared);
    }
}
