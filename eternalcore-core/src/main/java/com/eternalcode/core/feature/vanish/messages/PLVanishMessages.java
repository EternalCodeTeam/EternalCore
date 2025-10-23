package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLVanishMessages extends OkaeriConfig implements VanishMessages {

    Notice vanishEnabled = Notice.chat("<green>► <white>Włączono tryb niewidoczności!");
    Notice vanishDisabled = Notice.chat("<red>► <white>Wyłączono tryb niewidoczności!");

    Notice vanishEnabledForOther = Notice.chat("<green>► <white>Włączono tryb niewidzialności dla <green>{PLAYER}!");
    Notice vanishDisabledForOther = Notice.chat("<red>► <white>Wyłączono tryb niewidzialności dla <green>{PLAYER}!");

    Notice vanishEnabledByStaff = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>włączył Ci tryb niewidzialności!");
    Notice vanishDisabledByStaff = Notice.chat("<green>► <white>Administrator <green>{PLAYER} <white>wyłączył Ci tryb niewidzialności!");

    Notice currentlyInVanish = Notice.actionbar("<gradient:#9d6eef:#A1AAFF:#9d6eef>Jesteś obecnie niewidzialny!</gradient> <dark_gray>(<gray>/vanish</gray>)</dark_gray>");

    Notice joinedInVanish = Notice.chat("<green>► <white>Dołączyłeś do serwera w trybie niewidoczności.");
    Notice playerJoinedInVanish = Notice.chat("<green>► <white>{PLAYER} dołączył do serwera w trybie niewidoczności.");

    Notice cantBlockPlaceWhileVanished = Notice.chat("<red>Nie możesz stawiać bloków będąc niewidocznym.");
    Notice cantBlockBreakWhileVanished = Notice.chat("<red>Nie możesz niszczyć bloków będąc niewidocznym.");
    Notice cantUseChatWhileVanished = Notice.chat("<red>Nie możesz używać czatu będąc niewidocznym.");
    Notice cantDropItemsWhileVanished = Notice.chat("<red>Nie możesz upuszczać przedmiotów będąc niewidocznym.");
    Notice cantOpenInventoryWhileVanished = Notice.chat("<red>Nie możesz otwierać ekwipunku będąc niewidocznym.");
}
