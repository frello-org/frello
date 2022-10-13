package com.frello.lib;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Hash {
    private static final Argon2 argon2 = Argon2Factory.create();
    private static final int iterations = FrelloConfig.ARGON2_ITERATIONS;
    private static final int memory = FrelloConfig.ARGON2_MEMORY;

    public static String hash(String val) {
        return argon2.hash(iterations, memory, 1, val.toCharArray());
    }

    public static boolean verify(String hash, String val) {
        return argon2.verify(hash, val.toCharArray());
    }
}
