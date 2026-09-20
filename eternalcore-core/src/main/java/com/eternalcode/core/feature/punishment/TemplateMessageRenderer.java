package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TemplateMessageRenderer {

    private final MiniMessage miniMessage;

    @Inject
    TemplateMessageRenderer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    public List<Component> render(List<String> template, Map<String, String> placeholders) {
        return template.stream()
            .map(line -> this.replacePlaceholders(line, placeholders))
            .map(this.miniMessage::deserialize)
            .collect(Collectors.toList());
    }

    private String replacePlaceholders(String line, Map<String, String> placeholders) {
        String result = line;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
