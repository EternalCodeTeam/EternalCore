package com.eternalcode.core.ip;

import java.util.regex.Pattern;

public final class IpAddressValidator {

    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    private IpAddressValidator() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isValidIp(String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }
}
