package com.frello.services;

import com.frello.daos.auth.InvalidCredentialsException;
import com.frello.daos.auth.SQLAuthDAO;
import com.frello.models.auth.AuthInfo;
import com.frello.services.common.HttpException;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthService {
    @Data
    public static class LoginParams {
        private String username;
        private String password;
    }

    @Data
    @AllArgsConstructor
    public static class LoginResponse {
        private AuthInfo authInfo;
    }

    public LoginResponse login(LoginParams params) throws HttpException {
        var authDAO = new SQLAuthDAO();
        var username = params.getUsername();
        var password = params.getPassword();

        try {
            var authInfo = authDAO.authorize(username, password);
            return new LoginResponse(authInfo);
        } catch (InvalidCredentialsException e) {
            throw new HttpException(401, "Invalid credentials");
        }
    }
}
