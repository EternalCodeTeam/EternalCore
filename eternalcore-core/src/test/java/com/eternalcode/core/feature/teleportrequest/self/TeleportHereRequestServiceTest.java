package com.eternalcode.core.feature.teleportrequest.self;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeleportHereRequestServiceTest {

    private static final UUID TARGET = UUID.randomUUID();
    private static final UUID FIRST_REQUESTER = UUID.randomUUID();
    private static final UUID SECOND_REQUESTER = UUID.randomUUID();

    private TeleportHereRequestService service;

    @BeforeEach
    void setUp() {
        TeleportRequestSettings settings = mock(TeleportRequestSettings.class);
        when(settings.tpaRequestExpire()).thenReturn(Duration.ofMinutes(1));

        this.service = new TeleportHereRequestService(settings);
    }

    @Test
    void shouldFindLatestRequest() throws InterruptedException {
        this.service.createRequest(FIRST_REQUESTER, TARGET);
        Thread.sleep(5);
        this.service.createRequest(SECOND_REQUESTER, TARGET);

        assertThat(this.service.findLatestRequest(TARGET)).contains(SECOND_REQUESTER);
    }

    @Test
    void shouldFindPreviousRequestAfterLatestIsRemoved() throws InterruptedException {
        this.service.createRequest(FIRST_REQUESTER, TARGET);
        Thread.sleep(5);
        this.service.createRequest(SECOND_REQUESTER, TARGET);

        this.service.removeRequest(SECOND_REQUESTER);

        assertThat(this.service.findLatestRequest(TARGET)).contains(FIRST_REQUESTER);
    }

    @Test
    void shouldIgnoreRequestsSentToOtherPlayers() {
        this.service.createRequest(FIRST_REQUESTER, UUID.randomUUID());

        assertThat(this.service.findLatestRequest(TARGET)).isEmpty();
        assertThat(this.service.findRequests(TARGET)).isEmpty();
    }

    @Test
    void shouldReplaceRequestWhenRequesterSendsAgain() {
        UUID otherTarget = UUID.randomUUID();

        this.service.createRequest(FIRST_REQUESTER, otherTarget);
        this.service.createRequest(FIRST_REQUESTER, TARGET);

        assertThat(this.service.hasRequest(FIRST_REQUESTER, TARGET)).isTrue();
        assertThat(this.service.hasRequest(FIRST_REQUESTER, otherTarget)).isFalse();
        assertThat(this.service.findLatestRequest(otherTarget)).isEmpty();
    }

    @Test
    void shouldFindAllRequests() {
        this.service.createRequest(FIRST_REQUESTER, TARGET);
        this.service.createRequest(SECOND_REQUESTER, TARGET);

        assertThat(this.service.findRequests(TARGET)).containsExactlyInAnyOrder(FIRST_REQUESTER, SECOND_REQUESTER);
    }

}
