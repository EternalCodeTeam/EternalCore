package com.eternalcode.core.feature.automessage.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class PLAutoMessageMessages extends OkaeriConfig implements AutoMessageMessages {
    @Comment({
        "",
        "# Jeżeli chcesz użyć placeholder'a %server_online% musisz zainstalować plugin",
        "# PlaceholderAPI oraz pobrać placeholdery dla Server'a",
        "# za pomocą komendy /papi ecloud download Server",
    })
    public Map<String, Notice> messages = Map.of(
        "1", BukkitNotice.builder()
            .actionBar("<dark_gray>» <gold>Na serwerze jest: <white>%server_online% <gold>graczy online!")
            .sound(Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f)
            .build(),

        "2", BukkitNotice.builder()
            .chat("<dark_gray>» <gold>Potrzebujesz pomocy od admina?")
            .chat("<dark_gray>» <gold>Użyj komendy <white>/helpop <gold>aby zgłosić problem!")
            .chat("<dark_gray>» <green><click:suggest_command:'/helpop'>Kliknij aby wykonać!</click></green>")
            .sound(Sound.BLOCK_ANVIL_BREAK, 1.0f, 1.0f)
            .build()
    );

    public Notice enabled = Notice.chat("<green>► <white>Włączono automatyczne wiadomości!");
    public Notice disabled = Notice.chat("<green>► <white>Wyłączono automatyczne wiadomości!");

    @Override
    public Collection<Notice> messages() {
        return this.messages.values();
    }

    @Override
    public Notice enabled() {
        return this.enabled;
    }

    @Override
    public Notice disabled() {
        return this.disabled;
    }
}
