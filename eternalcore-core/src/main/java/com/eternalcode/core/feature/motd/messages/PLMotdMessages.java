package com.eternalcode.core.feature.motd.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLMotdMessages extends OkaeriConfig implements MotdMessages {

    @Comment("# Treść wiadomości dnia (MOTD), która zostanie wysłana do graczy po wejściu na serwer.")
    @Comment("# Domyślnie obsługiwane placeholdery: {PLAYER}, {WORLD}, {TIME}")
    @Comment("# Możesz dodać własne placeholdery korzystając z PlaceholderAPI.")
    @Comment("# Generator powiadomień znajdziesz tutaj: https://www.eternalcode.pl/notification-generator")
    public Notice motdContent = BukkitNotice.builder()
        .chat(List.of(
                "<green>Witaj na serwerze,</green> <gradient:#ee1d1d:#f1b722>{PLAYER}</gradient>",
                "<green>Miłej zabawy!</green>",
                "<green>Aktualny czas w {WORLD} to: </green><gradient:#2c60d5:#742ccf>{TIME}</gradient> <green>tików",
                "<green>Jeśli potrzebujesz pomocy, napisz do administracji komendą </green><dark_green><click:suggest_command:'/helpop'>/helpop</click></dark_green>"
            )
        )
        .title("<gradient:#9d6eef:#A1AAFF:#9d6eef>EternalCore</gradient>", "<white>Witamy ponownie na serwerze!")
        .sound(Sound.BLOCK_NOTE_BLOCK_PLING)
        .build();
}
