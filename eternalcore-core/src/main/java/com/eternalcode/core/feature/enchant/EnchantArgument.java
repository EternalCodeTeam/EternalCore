package com.eternalcode.core.feature.enchant;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Locale;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

@LiteArgument(type = Enchantment.class)
class EnchantArgument extends AbstractViewerArgument<Enchantment> {

    @Inject
    EnchantArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Enchantment> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(argument.toLowerCase(Locale.ROOT)));

        if (enchantment == null) {
            return ParseResult.failure(translation.enchant().invalidEnchantment());
        }

        return ParseResult.success(enchantment);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Enchantment> argument, SuggestionContext context) {
        return Arrays.stream(Enchantment.values())
            .map(Enchantment::getKey)
            .map(NamespacedKey::getKey)
            .collect(SuggestionResult.collector());
    }

}
