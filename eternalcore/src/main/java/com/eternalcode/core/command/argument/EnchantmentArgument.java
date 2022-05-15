package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import org.bukkit.enchantments.Enchantment;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("enchantment")
public class EnchantmentArgument implements OneArgument<Enchantment> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public EnchantmentArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Result<Enchantment, ?> parse(LiteInvocation invocation, String argument) {
        Enchantment enchantment = Enchantment.getByName(argument);

        if (enchantment == null) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            return Result.error(messages.argument().noEnchantment());
        }

        return Result.ok(enchantment);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(Enchantment.values())
            .map(Enchantment::getName)
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

}
