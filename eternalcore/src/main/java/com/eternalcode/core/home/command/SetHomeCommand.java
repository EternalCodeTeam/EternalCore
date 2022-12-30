package com.eternalcode.core.home.command;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.home.HomeManager;
import com.eternalcode.core.user.User;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.injector.Inject;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.stream.Stream;

@Route(name = "sethome")
public class SetHomeCommand {

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    public SetHomeCommand(HomeManager homeManager, NoticeService noticeService, PluginConfiguration pluginConfiguration) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute(required = 1)
    void execute(User user, Player player, @Arg @Name("home") String home) {
        this.setHome(user, player, home);
    }

    @Execute(required = 0)
    void execute(User user, Player player) {


        this.setHome(user, player, "home");
    }

    private void setHome(User user, Player player, String home) {
        int amountOfUserHomes = this.homeManager.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.getMaxAmountOfHomes(player);

        if (amountOfUserHomes >= this.getMaxAmountOfHomes(player)) {
            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                .notice(messages -> messages.home().limit())
                .send();

            return;
        }

        if (this.homeManager.hasHomeWithSpecificName(user, home)) {
            this.homeManager.createHome(user, home, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", home)
                .notice(messages -> messages.home().overrideHomeLocation())
                .send();

            return;
        }

        this.homeManager.createHome(user, home, player.getLocation());

        this.noticeService.create()
            .user(user)
            .notice(messages -> messages.home().create())
            .placeholder("{HOME}", home)
            .send();
    }

    public int getMaxAmountOfHomes(Player player) {
        Map<String, Integer> maxHomes = this.pluginConfiguration.homes.maxHomes;

        return maxHomes.entrySet().stream()
            .flatMap(entry -> {
                if (player.hasPermission(entry.getKey())) {
                    return Stream.of(entry.getValue());
                }

                return Stream.empty();
            })
            .max(Integer::compareTo)
            .orElse(0);
    }

}
