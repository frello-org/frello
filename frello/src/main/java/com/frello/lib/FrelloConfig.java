package com.frello.lib;

// FIXME: Ensure these variables are defined if `FRELLO_ENV` is `production`.
public class FrelloConfig {
    public static final String FRELLO_ENV = Env.getOr("FRELLO_ENV", "development");

    public static final String PGHOST = Env.getOr("PGHOST", "localhost");
    public static final int PGPORT = Integer.parseInt(Env.getOr("PGPORT", "5432"));
    public static final String PGUSER = Env.getOr("PGUSER", "postgres");
    public static final String PGPASSWORD = Env.getOr("PGPASSWORD", "postgres");
    public static final String PGDATABASE = Env.getOr("PGDATABASE", "local-postgres");

    public static final String JWT_SECRET = Env.getOr("JWT_SECRET", "development-secret::super-secret-token");

    public static final int ARGON2_ITERATIONS = Integer.parseInt(Env.getOr("ARGON2_ITERATIONS", "10"));
    public static final int ARGON2_MEMORY = Integer.parseInt(Env.getOr("ARGON2_MEMORY", "10"));
}
