package com.frello.daos.user;

import lombok.Getter;

public class CreateUserException extends Exception {
    @Getter
    private final Code code;

    public CreateUserException(Code code) {
        super("Could not create user");
        this.code = code;
    }

    public enum Code {
        CONFLICTING_USERNAME,
        CONFLICTING_EMAIL,
    }
}
