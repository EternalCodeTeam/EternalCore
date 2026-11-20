package com.eternalcode.core.feature.teleportrequest;

import java.time.Instant;
import java.util.UUID;

public record Request(UUID target, Instant createdAt) {}
