package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

@ArgumentName("material")
public class MaterialArgument implements SingleArgumentHandler<Material> {

    private final LanguageManager languageManager;

    public MaterialArgument(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }


    @Override
    public Material parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Material material = Material.getMaterial(argument.toUpperCase());

        if (material == null) {
            CommandSender sender = (CommandSender) invocation.sender().getSender();
            Messages messages = this.languageManager.getMessages(sender);

            throw new ValidationCommandException(messages.argument().noMaterial());
        }

        return material;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(Material.values())
            .map(Material::name)
            .map(String::toLowerCase)
            .toList();

    }
}
