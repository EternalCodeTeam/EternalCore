package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@ArgumentName("material")
public class MaterialArgument implements SingleArgumentHandler<Material> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public MaterialArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }


    @Override
    public Material parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Material material = Material.getMaterial(argument.toUpperCase());

        if (material == null) {
            Messages messages = userProvider.getUser(invocation)
                .map(languageManager::getMessages)
                .orElseGet(languageManager.getDefaultMessages());

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
