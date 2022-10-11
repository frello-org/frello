package com.frello.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class User {
    private final UUID id;
    private String username;

    private String firstName;
    private String lastName;

    private boolean deleted;
    private OffsetDateTime deletionTime;
    private final OffsetDateTime creationTime;

    private final AdminActor admin;
    private final ServiceConsumerActor consumer;
    private final ServiceProviderActor provider;
}
