package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@ArgumentName("enchantment")
public class EnchantmentArgument extends AbstractViewerArgument<Enchantment> {

    public EnchantmentArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        super(viewerProvider, languageManager);
    }

    @Override
    public Result<Enchantment, Notification> parse(LiteInvocation invocation, String argument, Messages messages) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument));

        if (enchantment == null) {
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
            .toList();
    }

}
