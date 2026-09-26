package com.eternalcode.core.feature.enderchest.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;

@Getter
@Accessors(fluent = true)
public class PLEnderchestMessages extends OkaeriConfig implements EnderchestMessages {

    Notice openedEnderchest = Notice.chat("<color:#9d6eef>► <white>Otworzono enderchest!");
    Notice customEnderchestDisabled = Notice.chat("<red>✘ <dark_red>Custom enderchesty są wyłączone na tym serwerze!");
    Notice enderchestsBlocked = Notice.chat("<red>✘ <dark_red>Enderchesty są wyłączone na tym serwerze!");

    @Comment({ " ", "# {PAGE} - Numer strony, {PAGES} - Liczba dostępnych stron" })
    Notice openedEnderchestPage = BukkitNotice.builder()
        .actionBar("<color:#9d6eef>► <white>Strona enderchestu <color:#9d6eef>{PAGE}<white>/<color:#9d6eef>{PAGES}")
        .sound(Sound.BLOCK_ENDER_CHEST_OPEN, 0.5f, 1f)
        .build();

    Notice enderchestPageUnavailable = Notice.chat("<red>✘ <dark_red>Strona <red>{PAGE} <dark_red>jest niedostępna! Możesz korzystać z <red>{PAGES} <dark_red>stron(y).");

    @Comment({ " ", "# {TIME} - Czas do następnej zmiany strony" })
    Notice enderchestPageSwitchDelay = Notice.chat("<red>✘ <dark_red>Możesz zmienić stronę dopiero za <red>{TIME}<dark_red>!");

    @Comment({ " ", "# {PLAYER} - Właściciel enderchestu, {PAGE} - Numer strony, {PAGES} - Liczba dostępnych stron" })
    Notice openedTargetPlayerEnderchest = Notice.chat("<color:#9d6eef>► <white>Otworzono stronę <color:#9d6eef>{PAGE} <white>enderchestu gracza {PLAYER}!");

    @Comment({ " ", "# {PLAYER} - Właściciel enderchestu" })
    Notice playerEnderchestEmpty = Notice.chat("<red>✘ <dark_red>Gracz <red>{PLAYER} <dark_red>nie ma nic zapisanego! "
        + "Jeśli nie logował się od włączenia custom enderchestów, jego przedmioty są nadal w zwykłym enderchescie.");
    Notice enderchestInUse = Notice.chat("<red>✘ <dark_red>Enderchest gracza <red>{PLAYER} <dark_red>jest otwarty gdzie indziej, spróbuj za chwilę!");
}
