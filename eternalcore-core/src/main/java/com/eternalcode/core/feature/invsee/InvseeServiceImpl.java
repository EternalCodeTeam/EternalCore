package com.eternalcode.core.feature.invsee;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Service
public class InvseeServiceImpl implements InvseeService {

    private final NoticeService noticeService;

    @Inject
    public InvseeServiceImpl(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Override
    public void openInventory(UUID requester, UUID target) {
        Player player = Bukkit.getPlayer(requester);
        Player targetPlayer = Bukkit.getPlayer(target);

        assert player != null;
        assert targetPlayer != null;

        player.openInventory(targetPlayer.getInventory());
    }
}
