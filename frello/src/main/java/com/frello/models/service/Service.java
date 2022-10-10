package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerActor;
import com.frello.models.user.ServiceProviderActor;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class Service {
    private final UUID id;

    private State state;

    private UUID classId;
    private transient Optional<ServiceClass> serviceClass;

    private UUID providerId;
    private transient Optional<ServiceProviderActor> provider;

    private UUID consumerId;
    private transient Optional<ServiceConsumerActor> consumer;

    private final OffsetDateTime creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN,
    }
}
