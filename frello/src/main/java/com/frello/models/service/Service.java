package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerActor;
import com.frello.models.user.ServiceProviderActor;

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
    private ServiceProviderActor provider;

    @NonNull
    private UUID consumerId;
    private ServiceConsumerActor consumer;

    @NonNull
    private final OffsetDateTime creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN,
    }
}
