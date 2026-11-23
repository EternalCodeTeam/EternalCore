package com.eternalcode.core.feature.kit;


import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.description.Description;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

@Command(name = "kitedit")
@Permission("eternalcore.kit.edit")
@Description("Command used to edit kits")
public class KitEditCommand {

    private final static String EDIT_SUFFIX = "kitedit";

    private final NoticeService noticeService;
    private final KitService kitService;

    @Inject
    public KitEditCommand(NoticeService noticeService, KitService kitService) {
        this.noticeService = noticeService;
        this.kitService = kitService;
    }

    //edit gui
    @Execute
    void execute(@Sender Player player, @Arg Kit kit) {

        this.kitService.openKit(player, kit.name());

        this.noticeService.create()
            .notice(translation -> translation.kit().kitEditGuiOpened())
            .player(player.getUniqueId())
            .send();

    }

    //change name
    @Execute(name = "changename")
    void execute(@Sender CommandSender sender, @Arg("<current kit name>") Kit kit, @Arg("<new name>") String newName) {
        if (newName.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitNameEmpty())
                .sender(sender)
                .send();

            return;
        }

        if (newName.startsWith(EDIT_SUFFIX)) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitNameSameAsPermission())
                .sender(sender)
                .send();
        }

        String oldName = kit.name();
        if (Objects.equals(oldName, newName)) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitNameUnchanged())
                .sender(sender)
                .send();

            return;
        }

        this.kitService.changeName(oldName, newName);

        this.noticeService.create()
            .notice(translation -> translation.kit().kitRenamed())
            .placeholder("{OLD_NAME}", oldName)
            .placeholder("{NEW_NAME}", newName)
            .sender(sender)
            .send();
    }

    //create
    //create from current eq
    @Execute(name = "create")
    void execute(@Sender CommandSender sender, @Arg("<new kit name>") String kitName, @Arg("<from invetory>") Optional<Boolean> fromInventory) {
        Boolean fromInventoryValue = fromInventory.orElse(false);

        if (!(sender instanceof Player player)) {
            this.createEmptyInventory(sender, kitName);
            return;
        }

        if (fromInventoryValue) {
            createEmptyInventory(sender, kitName);

            return;
        }

        PlayerInventory inventory = player.getInventory();
        boolean successfullyCreate = this.kitService.createKit(kitName, inventory);

        if (successfullyCreate) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitCreatedFromInventory())
                .placeholder("{NAME}", kitName)
                .sender(sender)
                .send();

            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.kit().kitCreateFailed())
            .placeholder("{NAME}", kitName)
            .sender(sender)
            .send();

    }

    private void createEmptyInventory(CommandSender sender, String kitName) {
        boolean emptyKitSuccessful = this.kitService.createEmptyKit(kitName);

        if (emptyKitSuccessful) {
            this.noticeService.create()
                .notice(translation -> translation.kit().kitEmptyCreated())
                .placeholder("{NAME}", kitName)
                .sender(sender)
                .send();

            return;
        }
    }

    //delete
    @Execute(name = "delete")
    void execute(@Sender CommandSender sender, @Arg("<kit name>") Kit kit) {

        String kitName = kit.name();
        this.kitService.deleteKit(kitName);

        this.noticeService.create()
            .notice(translation -> translation.kit().kitDeleted())
            .placeholder("{NAME}", kitName)
            .sender(sender)
            .send();

    }

}
