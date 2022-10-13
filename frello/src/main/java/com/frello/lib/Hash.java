package com.frello.lib;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Hash {
    private static final Argon2 argon2;
    private static final int iterations;
    private static final int memory;

    static {
        argon2 = Argon2Factory.create();
        iterations = Integer.parseInt(Env.getOr("ARGON2_ITERATIONS", "1000"));
        memory = Integer.parseInt(Env.getOr("ARGON2_MEMORY", "65536"));
    }

    public static String hash(String val) {
        return argon2.hash(iterations, memory, 1, val.toCharArray());
    }

    public static boolean verify(String hash, String val) {
        return argon2.verify(hash, val.toCharArray());
    }
}
