package com.eternalcode.core.feature.vanish.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLVanishMessages extends OkaeriConfig implements VanishMessages {

    public Notice vanishEnabled = Notice.chat("<green>► <white>Włączono tryb niewidoczności!");
    public Notice vanishDisabled = Notice.chat("<red>► <white>Wyłączono tryb niewidoczności!");

    public Notice vanishEnabledOther = Notice.chat("<green>► <white>{PLAYER} włączył tryb niewidoczności!");
    public Notice vanishDisabledOther = Notice.chat("<red>► <white>{PLAYER} wyłączył tryb niewidoczności!");

    public Notice currentlyInVanish = Notice.actionbar("<green>► <aqua>Jesteś niewidoczny!");

    public Notice joinedInVanish = Notice.chat("<green>► <white>Dołączyłeś do serwera w trybie niewidoczności.");
    public Notice playerJoinedInVanish = Notice.chat("<green>► <white>{PLAYER} dołączył do serwera w trybie niewidoczności.");

    public Notice cantBlockPlaceWhileVanished = Notice.chat("<red>Nie możesz stawiać bloków będąc niewidocznym.");
    public Notice cantBlockBreakWhileVanished = Notice.chat("<red>Nie możesz niszczyć bloków będąc niewidocznym.");
    public Notice cantUseChatWhileVanished = Notice.chat("<red>Nie możesz używać czatu będąc niewidocznym.");
    public Notice cantDropItemsWhileVanished = Notice.chat("<red>Nie możesz upuszczać przedmiotów będąc niewidocznym.");
    public Notice cantPickupItemsWhileVanished = Notice.chat("<red>Nie możesz zbierać przedmiotów będąc niewidocznym.");
    public Notice cantOpenInventoryWhileVanished = Notice.chat("<red>Nie możesz otwierać ekwipunku będąc niewidocznym.");


}
