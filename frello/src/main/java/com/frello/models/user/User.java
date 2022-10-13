package com.frello.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class User {
    @NonNull
    private final UUID id;
    @NonNull
    private String username;
    @NonNull
    @JsonIgnore
    private transient String passwordHash;
    @NonNull
    private String email;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;

    private boolean deleted;
    private OffsetDateTime deletionTime;
    @NonNull
    private final OffsetDateTime creationTime;

    private final AdminActor admin;
    private final ServiceConsumerActor consumer;
    private final ServiceProviderActor provider;

    public boolean isAdmin() {
        return admin != null;
    }

    public boolean isConsumer() {
        return consumer != null;
    }

    public boolean isProvider() {
        return provider != null;
    }
}
