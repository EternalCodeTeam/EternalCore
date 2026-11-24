package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "kitadmin icon")
@Permission("eternalcore.kits.admin.icon")
class KitIconCommand {

    private final KitAdminSaver saver;
    private final KitNotices notices;

    @Inject
    KitIconCommand(KitAdminSaver saver, KitNotices notices) {
        this.saver = saver;
        this.notices = notices;
    }

    @Execute
    @DescriptionDocs(description = "Sets kit GUI icon to the item in hand (name/lore of the item are kept)", arguments = "<kit>")
    void setIcon(@Sender Player player, @Arg Kit kit) {
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand == null || hand.getType() == Material.AIR || hand.getAmount() <= 0) {
            this.notices.send(player, KitMessages::emptyHand);
            return;
        }

        ItemStack icon = hand.clone();
        icon.setAmount(1);

        this.saver.save(player, kit.toBuilder().icon(icon).build(), KitMessages::iconChanged);
    }
}
