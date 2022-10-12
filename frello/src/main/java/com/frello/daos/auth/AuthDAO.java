package com.frello.daos.auth;

import com.frello.models.auth.AuthInfo;

public interface AuthDAO {
    AuthInfo authorize(String username, String password) throws InvalidCredentialsException;
}
