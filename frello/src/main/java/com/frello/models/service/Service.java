package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerUser;
import com.frello.models.user.ServiceProviderUser;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class Service {
    @NonNull
    private final UUID id;

    @NonNull
    private State state;

    @NonNull
    private UUID classId;
    private ServiceClass serviceClass;

    @NonNull
    private UUID providerId;
    private ServiceProviderUser provider;

    @NonNull
    private UUID consumerId;
    private ServiceConsumerUser consumer;

    @NonNull
    private final Instant creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN,
    }
}
