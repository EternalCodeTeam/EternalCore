package com.eternalcode.core.feature.motd.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class PLMotdMessages extends OkaeriConfig implements MotdMessages {

    @Comment("# Treść wiadomości dnia (MOTD), która zostanie wysłana do graczy po wejściu na serwer.")
    @Comment("# Domyślnie obsługiwane placeholdery: {PLAYER}, {WORLD}")
    @Comment("# Możesz dodać własne placeholdery korzystając z PlaceholderAPI.")
    @Comment("# Generator powiadomień znajdziesz tutaj: https://www.eternalcode.pl/notification-generator")
    Notice motdContent = BukkitNotice.builder()
        .chat(List.of(
                " ",
                " <color:#9d6eef>🠚 <white>Witaj z powrotem, <gradient:#9d6eef:#A1AAFF:#9d6eef>{PLAYER}</gradient>!</white>",
                " ",
                " <white>Przydatne komendy:",
                "  <color:#9d6eef>⏺ <white>/discord - link do discorda",
                "  <color:#9d6eef>⏺ <white>/help - pomoc serwerowa",
                "  <color:#9d6eef>⏺ <white>/dynmap - mapa serwera"
            )
        )
        .title("<gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore</gradient>", "<white>Witamy ponownie na serwerze!")
        .sound(Sound.BLOCK_NOTE_BLOCK_PLING)
        .build();
}
