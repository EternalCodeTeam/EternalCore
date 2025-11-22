package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLNearMessages extends OkaeriConfig implements NearMessages {

    @Comment("# Dostępne placeholdery: {RADIUS} - zasięg wyszukiwania stworzeń")
    Notice entitiesNotFound = Notice.chat("<red>► <white>Nie znaleziono żadnych stworzeń w zasięgu {RADIUS} bloków. Spróbuj ponownie z większym zasięgiem");

    @Comment({
        "# Dostępne placeholdery: ",
        "{ENTITY_AMOUNT} - ilość znalezionych i wyświetlonych stworzeń,",
        "{RADIUS} - zasięg wyszukiwania stworzeń"
    })
    Notice entitiesFound = Notice.chat("<color:#9d6eef>► <white>Liczba <bold>{ENTITY_AMOUNT}</bold> stworzeń w zasięgu: <bold>{RADIUS} bloków</bold>:");

    @Comment("# Wejście na lisćie wyszukanych stworzeń. Dostępne placeholdery: {ENTITY_TYPE} - typ stworzenia, {COUNT} - ilość stworzeń danego typu")
    Notice entityEntry = Notice.chat("<gray>- <white>{ENTITY_TYPE}: <color:#9d6eef>{COUNT}");
    Notice invalidEntityType = Notice.chat("<red>✘ <dark_red>Nie podano prawidłowego zakresu entity! Użyj sugerowanej opcji.");

}
