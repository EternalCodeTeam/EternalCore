package com.eternalcode.core.chat.tag;

import com.eternalcode.core.utils.ChatUtils;
import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class LegacyColorProcessor implements UnaryOperator<Component>  {

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
            .replacement((matchResult, builder1) -> ChatUtils.component(matchResult.group())));
    }
}
