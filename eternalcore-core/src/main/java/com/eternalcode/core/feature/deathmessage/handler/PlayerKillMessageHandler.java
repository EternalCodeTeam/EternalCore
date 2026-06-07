package com.eternalcode.core.feature.deathmessage.handler;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.commons.RandomElementUtil;
import com.eternalcode.core.feature.deathmessage.DeathContext;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.MaterialUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Service
public class PlayerKillMessageHandler {

    private static final Material AIR = MaterialUtil.parseRequired(XMaterial.AIR);

    private final NoticeService noticeService;
    private final TranslationManager translationManager;

    @Inject
    public PlayerKillMessageHandler(NoticeService noticeService, TranslationManager translationManager) {
        this.noticeService = noticeService;
        this.translationManager = translationManager;
    }

    public void handle(DeathContext context) {
        Player victim = context.victim();
        Player killer = context.killer();

        if (victim == null || killer == null) {
            return;
        }

        String victimName = victim.getName();
        String killerName = killer.getName();

        ItemStack weapon = killer.getInventory().getItemInMainHand().getType() != AIR
            ? killer.getInventory().getItemInMainHand()
            : killer.getInventory().getItemInOffHand();
        String weaponName = this.getWeaponName(weapon);

        this.noticeService.create()
            .noticeOptional(translation -> RandomElementUtil.randomElement(translation.deathMessage()
                .playerKilledByOtherPlayer()))
            .placeholder("{PLAYER}", victimName)
            .placeholder("{KILLER}", killerName)
            .placeholder("{WEAPON}", weaponName)
            .onlinePlayers()
            .sendAsync();
    }

    private String getWeaponName(ItemStack weapon) {
        if (weapon == null || weapon.getType() == AIR) {
            return this.translationManager.getMessages().deathMessage().unarmedWeaponName();
        }

        if (weapon.hasItemMeta() && weapon.getItemMeta().hasDisplayName()) {
            return weapon.getItemMeta().getDisplayName();
        }

        return MaterialUtil.format(weapon.getType());
    }
}
