package com.eternalcode.core.command.argument;

import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
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

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;

    public MaterialArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Result<Material, String> parse(LiteInvocation invocation, String argument) {
        Material material = Material.getMaterial(argument.toUpperCase());

        if (material == null) {
            Viewer viewer = this.viewerProvider.any(invocation);
            Messages messages = this.languageManager.getMessages(viewer);

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
