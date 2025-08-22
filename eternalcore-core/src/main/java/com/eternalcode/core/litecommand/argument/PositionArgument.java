package com.eternalcode.core.litecommand.argument;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteArgument(type = Position.class)
public class PositionArgument extends AbstractViewerArgument<Position> {

    @Inject
    PositionArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Position> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            Position position = Position.parse(argument);
            return ParseResult.success(position);
        } catch (IllegalArgumentException exception) {
            return ParseResult.failure(translation.argument().invalidPosition());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Position> argument, SuggestionContext context) {
        CommandSender sender = invocation.sender();

        if (sender instanceof Player player) {
            org.bukkit.Location location = player.getLocation();

            Position currentPosition = new Position(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                location.getWorld().getName()
            );

            return SuggestionResult.of(currentPosition.toString());
        }

        return SuggestionResult.of("Position{x=0.0, y=64.0, z=0.0, yaw=0.0, pitch=0.0, world='world'}");
    }
}
