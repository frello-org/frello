package com.frello.lib;

import java.util.Optional;

// XX: Check me at boundary!
public class InternalException extends RuntimeException {
    public final Optional<Throwable> cause;

    public InternalException(String message) {
        super("InternalException: " + message);
        cause = Optional.empty();
    }

    public InternalException(Throwable cause) {
        super("InternalException: " + cause.toString());
        this.cause = Optional.of(cause);
    }
}
