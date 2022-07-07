package com.eternalcode.core.command.argument;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("enchantment")
public class EnchantmentArgument implements OneArgument<Enchantment> {

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;

    public EnchantmentArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Result<Enchantment, ?> parse(LiteInvocation invocation, String argument) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument));

        if (enchantment == null) {
            Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
            Messages messages = this.languageManager.getMessages(viewer);

            return Result.error(messages.argument().noEnchantment());
        }

        return Result.ok(enchantment);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(Enchantment.values())
            .map(Enchantment::getKey)
            .map(NamespacedKey::getKey)
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

}
