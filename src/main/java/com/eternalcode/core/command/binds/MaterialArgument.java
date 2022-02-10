package com.eternalcode.core.command.binds;

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

    @Override
    public Material parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Material material = Material.getMaterial(argument);


        if (material == null) {
            throw new ValidationCommandException(ValidationInfo.CUSTOM, "nie ma tekiego materialu");
        }

        return material;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        Material[] materials = Material.class.getEnumConstants();

        return Arrays.stream(materials)
            .map(Material::name)
            .toList();

    }
}
