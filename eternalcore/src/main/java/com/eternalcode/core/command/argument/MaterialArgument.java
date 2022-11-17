package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Material;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@ArgumentName("material")
public class MaterialArgument implements OneArgument<Material> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public MaterialArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Result<Material, String> parse(LiteInvocation invocation, String argument) {
        Material material = Material.getMaterial(argument.toUpperCase());

        if (material == null) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            return Result.error(messages.argument().noMaterial());
        }

        return Result.ok(material);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(Material.values())
            .map(Material::name)
            .map(String::toLowerCase)
            .map(Suggestion::of)
            .toList();

    }

}
