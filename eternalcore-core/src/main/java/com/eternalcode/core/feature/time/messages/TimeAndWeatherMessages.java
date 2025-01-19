package com.eternalcode.core.feature.time.messages;

import com.eternalcode.multification.notice.Notice;

public interface TimeAndWeatherMessages {
    Notice timeSetDay();
    Notice timeSetNight();

    Notice timeSet();
    Notice timeAdd();

    Notice weatherSetRain();
    Notice weatherSetSun();
    Notice weatherSetThunder();
}
