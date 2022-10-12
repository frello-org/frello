package com.frello.models.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    private final UUID id;

    private State state;

    private UUID requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceRequest request;

    private UUID providerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceProviderActor provider;

    private UUID consumerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceConsumerActor consumer;

    private final OffsetDateTime creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN,
    }
}
