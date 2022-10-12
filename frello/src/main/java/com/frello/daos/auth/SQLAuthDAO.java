package com.frello.daos.auth;

import java.util.Optional;

import com.frello.daos.user.SQLUserDAO;
import com.frello.lib.Hash;
import com.frello.models.auth.AuthInfo;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public AuthInfo authorize(String username, String password) throws InvalidCredentialsException {
        var userDAO = new SQLUserDAO();

        var user = userDAO
                .user(username)
                .orElseThrow(InvalidCredentialsException::new);

        if (!Hash.verify(user.getPasswordHash(), password)) {
            throw new InvalidCredentialsException();
        }

        return new AuthInfo(user);
    }

}
