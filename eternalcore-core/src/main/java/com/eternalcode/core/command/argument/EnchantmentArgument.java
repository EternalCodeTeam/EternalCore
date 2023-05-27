package com.eternalcode.core.command.argument;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
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

    public EnchantmentArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<Enchantment, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument));

        if (enchantment == null) {
            return Result.error(translation.argument().noEnchantment());
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
