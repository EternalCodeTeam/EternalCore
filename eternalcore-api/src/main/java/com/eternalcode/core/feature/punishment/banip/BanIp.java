package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;

/**
 * Ban of an IP address; {@code target} is the player the ban was issued for. Created only by {@link BanIpService}.
 */
public interface BanIp extends ExpirablePunishment {

    String ip();
}
