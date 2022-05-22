package com.eternalcode.core.command.implementation;


import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.CommandSection;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import panda.std.Result;
import panda.std.stream.PandaStream;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Section(route = "permssion-invoke")
@Permission("eternalcore.command.permssion.invoke")
public class PermissionInvokerCommand {

    @Execute
    public void execute(CommandSender sender, @Arg @By("permission") String permission) {
        Bukkit.dispatchCommand(sender, "lp group default permission set " + permission);
    }

    public static class Argument implements OneArgument<String> {

        private final Supplier<LiteCommands<CommandSender>> liteCommands;

        public Argument(Supplier<LiteCommands<CommandSender>> liteCommands) {
            this.liteCommands = liteCommands;
        }

        @Override
        public Result<String, ?> parse(LiteInvocation invocation, String argument) {
            return Result.ok(argument);
        }

        @Override
        public List<Suggestion> suggest(LiteInvocation invocation) {
            List<Suggestion> collect = PandaStream.of(liteCommands.get().getCommandService().getSections())
                .flatMap(CommandSection::permissions)
                .map(Suggestion::of)
                .collect(Collectors.toList());

            return PandaStream.of(liteCommands.get().getCommandService().getSections())
                .flatMap(CommandSection::childrenSection)
                .flatMap(CommandSection::permissions)
                .map(Suggestion::of)
                .concat(collect)
                .collect(Collectors.toList());

        }
    }

}

