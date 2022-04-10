package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("enchantment")
public class EnchantmentArgument implements SingleArgumentHandler<Enchantment> {

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public EnchantmentArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Enchantment parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Enchantment enchantment = Enchantment.getByName(argument);

        if (enchantment == null) {
            Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

            throw new ValidationCommandException(messages.argument().noEnchantment());
        }

        return enchantment;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(Enchantment.values())
            .map(Enchantment::getName)
            .collect(Collectors.toList());
    }
}
