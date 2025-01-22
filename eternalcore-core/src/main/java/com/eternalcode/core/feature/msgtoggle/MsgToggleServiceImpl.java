package com.eternalcode.core.feature.msgtoggle;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Blocking;

@Service
public class MsgToggleServiceImpl implements MsgToggleService{

    private final MsgToggleRepository msgToggleRepository;

    @Inject
    public MsgToggleServiceImpl(MsgToggleRepository msgToggleRepository) {
        this.msgToggleRepository = msgToggleRepository;
    }


    @Override
    public CompletableFuture<Boolean> hasMsgToggled(UUID uuid) {
        return this.msgToggleRepository.isToggled(uuid);
    }

    @Override
    @Blocking
    public void toggleMsg(UUID uuid, boolean toggle) {
        this.msgToggleRepository.setToggledOff(uuid, toggle);
    }
}
