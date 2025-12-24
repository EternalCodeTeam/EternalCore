package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLVanishMessages extends OkaeriConfig implements VanishMessages {

    Notice vanishEnabled = Notice.chat("<color:#9d6eef>► <white>Włączono tryb niewidoczności!");
    Notice vanishDisabled = Notice.chat("<red>✘ <white>Wyłączono tryb niewidoczności!");

    Notice vanishEnabledForOther = Notice
            .chat("<color:#9d6eef>► <white>Włączono tryb niewidzialności dla <color:#9d6eef>{PLAYER}!");
    Notice vanishDisabledForOther = Notice
            .chat("<red>✘ <white>Wyłączono tryb niewidzialności dla <color:#9d6eef>{PLAYER}!");

    Notice vanishEnabledByStaff = Notice.chat(
            "<color:#9d6eef>► <white>Administrator <color:#9d6eef>{PLAYER} <white>włączył Ci tryb niewidzialności!");
    Notice vanishDisabledByStaff = Notice
            .chat("<red>✘ <white>Administrator <color:#9d6eef>{PLAYER} <white>wyłączył Ci tryb niewidzialności!");

    Notice currentlyInVanish = Notice.actionbar(
            "<gradient:#9d6eef:#A1AAFF:#9d6eef>Jesteś obecnie niewidzialny!</gradient> <dark_gray>(<gray>/vanish</gray>)</dark_gray>");

    Notice joinedInVanish = Notice.chat("<color:#9d6eef>► <white>Dołączyłeś do serwera w trybie niewidoczności.");
    Notice playerJoinedInVanish = Notice
            .chat("<color:#9d6eef>► <white>{PLAYER} dołączył do serwera w trybie niewidoczności.");

    Notice cantBlockPlaceWhileVanished = Notice.chat("<red>✘ <dark_red>Nie możesz stawiać bloków będąc niewidocznym.");
    Notice cantBlockBreakWhileVanished = Notice.chat("<red>✘ <dark_red>Nie możesz niszczyć bloków będąc niewidocznym.");
    Notice cantUseChatWhileVanished = Notice.chat("<red>✘ <dark_red>Nie możesz używać czatu będąc niewidocznym.");
    Notice cantDropItemsWhileVanished = Notice
            .chat("<red>✘ <dark_red>Nie możesz upuszczać przedmiotów będąc niewidocznym.");
    Notice cantOpenInventoryWhileVanished = Notice
            .chat("<red>✘ <dark_red>Nie możesz otwierać ekwipunku będąc niewidocznym.");
}
