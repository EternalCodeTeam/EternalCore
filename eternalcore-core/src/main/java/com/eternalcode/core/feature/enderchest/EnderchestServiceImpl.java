package com.eternalcode.core.feature.enderchest;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Service
class EnderchestServiceImpl implements EnderchestService {

    private final EnderchestInventory enderchestInventory;
    private final EnderchestManager enderchestManager;
    private final EnderchestSettings settings;

    @Inject
    EnderchestServiceImpl(EnderchestInventory enderchestInventory, EnderchestManager enderchestManager, EnderchestSettings settings) {
        this.enderchestInventory = enderchestInventory;
        this.enderchestManager = enderchestManager;
        this.settings = settings;
    }

    @Override
    public boolean areEnderchestsBlocked() {
        return this.settings.enderchestsBlocked();
    }

    @Override
    public boolean isVanillaEnderchestReplaced() {
        return !this.areEnderchestsBlocked() && this.settings.replaceVanillaEnderchest();
    }

    @Override
    public void openEnderchest(Player player) {
        if (this.areEnderchestsBlocked()) {
            return;
        }

        if (!this.isVanillaEnderchestReplaced()) {
            player.openInventory(player.getEnderChest());
            return;
        }

        this.enderchestInventory.openPage(player, player, EnderchestLayout.FIRST_PAGE);
    }

    @Override
    public void openEnderchest(Player viewer, OfflinePlayer owner, int page) {
        if (!this.isVanillaEnderchestReplaced()) {
            return;
        }

        this.enderchestInventory.openPage(viewer, owner, page);
    }

    @Override
    public int getPageLimit(Player player) {
        return this.enderchestManager.getPageLimit(player);
    }
}
