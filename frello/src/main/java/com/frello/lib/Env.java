package com.frello.lib;

public class Env {
    public static String get(String name) {
        var value = System.getenv(name);
        if (value == null) {
            throw new RuntimeException(String.format(
                    "Missing required environment variable `%s`", name));
        }
        return value;
    }

    public static String getOr(String name, String fallback) {
        var value = System.getenv(name);
        return value != null ? value : fallback;
    }
}
