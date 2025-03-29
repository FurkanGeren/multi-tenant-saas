package org.saas.core.utils;

public final class StringUtils {
    private StringUtils() {}

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
