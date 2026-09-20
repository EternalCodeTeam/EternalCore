package com.eternalcode.core.feature.home;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.home.event.HomeLimitReachedEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import java.util.UUID;
import org.bukkit.entity.Player;

@Service
public class HomeMutationService {

    private final HomeService homeService;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;

    @Inject
    public HomeMutationService(HomeService homeService, NoticeService noticeService, EventCaller eventCaller) {
        this.homeService = homeService;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
    }

    public void setOrOverrideHome(User user, Player player, String homeName) {
        UUID uniqueId = user.getUniqueId();

        if (this.homeService.hasHome(uniqueId, homeName)) {
            this.homeService.createHome(uniqueId, homeName, player.getLocation());

            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", homeName)
                .notice(translation -> translation.home().overrideHomeLocation())
                .send();

            return;
        }

        int amountOfUserHomes = this.homeService.getHomes(player.getUniqueId()).size();
        int maxAmountOfUserHomes = this.homeService.getHomeLimit(player);

        if (amountOfUserHomes >= maxAmountOfUserHomes) {
            this.noticeService.create()
                .user(user)
                .placeholder("{LIMIT}", String.valueOf(maxAmountOfUserHomes))
                .notice(translation -> translation.home().limit())
                .send();

            this.eventCaller.callEvent(new HomeLimitReachedEvent(
                player.getUniqueId(),
                maxAmountOfUserHomes,
                amountOfUserHomes
            ));

            return;
        }

        this.homeService.createHome(uniqueId, homeName, player.getLocation());
        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().create())
            .placeholder("{HOME}", homeName)
            .send();
    }

    public void renameHome(User user, Home home, String newName) {
        UUID uniqueId = user.getUniqueId();
        String oldName = home.getName();

        if (newName.equals(oldName)) {
            return;
        }

        if (this.homeService.hasHome(uniqueId, newName)) {
            this.noticeService.create()
                .user(user)
                .placeholder("{HOME}", newName)
                .notice(translation -> translation.home().renameNameTaken())
                .send();

            return;
        }

        Home renamedHome = this.homeService.createHome(uniqueId, newName, home.getLocation());

        if (renamedHome == null) {
            return;
        }

        this.homeService.deleteHome(uniqueId, oldName);

        this.noticeService.create()
            .user(user)
            .placeholder("{HOME_OLD}", oldName)
            .placeholder("{HOME_NEW}", newName)
            .notice(translation -> translation.home().renamed())
            .send();
    }

    public void deleteHome(User user, Home home) {
        this.homeService.deleteHome(user.getUniqueId(), home.getName());

        this.noticeService.create()
            .user(user)
            .notice(translation -> translation.home().delete())
            .placeholder("{HOME}", home.getName())
            .send();
    }
}
