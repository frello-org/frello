package com.frello.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

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
    private String firstName;
    @NonNull
    private String lastName;

    private boolean isDeleted;
    @NonNull
    private OffsetDateTime deletionTime;
    @NonNull
    private final OffsetDateTime creationTime;

    private final AdminActor admin;
    private final ServiceConsumerActor consumer;
    private final ServiceProviderActor provider;
}
