package com.eternalcode.core.command.binds;

import com.eternalcode.core.configuration.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@ArgumentName("material")
public class MaterialArgument implements SingleArgumentHandler<Material> {

    private final MessagesConfiguration messages;

    public MaterialArgument(MessagesConfiguration messages){
        this.messages = messages;
    }

    @Override
    public Material parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Material material = Material.getMaterial(argument);


        if (material == null) {
            throw new ValidationCommandException(ValidationInfo.CUSTOM, this.messages.argumentSection.noMaterial);
        }

        return material;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(Material.values())
            .map(Material::name)
            .map(String::toUpperCase)
            .toList();

    }
}
