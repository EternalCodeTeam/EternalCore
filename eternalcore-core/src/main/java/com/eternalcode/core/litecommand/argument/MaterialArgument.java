package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@LiteArgument(type = Material.class)
public class MaterialArgument extends AbstractViewerArgument<Material> {

    @Inject
    MaterialArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Material> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            Material material = Material.valueOf(argument.toUpperCase());
            return ParseResult.success(material);
        } catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.argument().noMaterial());
        }
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Material> argument,
        SuggestionContext context
    ) {
        return Arrays.stream(Material.values())
            .map(Material::name)
            .map(String::toLowerCase)
            .collect(SuggestionResult.collector());
    }
}
