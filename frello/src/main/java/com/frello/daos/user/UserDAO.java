package com.frello.daos.user;

import java.util.Optional;
import java.util.UUID;

import com.frello.models.user.User;

public interface UserDAO {
    Optional<User> user(UUID id);

    Optional<User> user(String id);

    void create(User user) throws CreateUserException;
}
