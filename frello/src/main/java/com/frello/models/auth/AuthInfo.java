package com.frello.models.auth;

import com.frello.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthInfo {
    @NonNull
    private final User user;
}
