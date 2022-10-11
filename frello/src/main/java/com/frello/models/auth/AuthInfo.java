package com.frello.models.auth;

import com.frello.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthInfo {
    private final User user;
}
