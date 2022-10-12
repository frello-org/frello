package com.frello.models.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.frello.models.user.User;
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
public class Service {
    private final UUID id;

    private State state;

    private UUID requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceRequest request;

    private UUID providerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User provider;

    private UUID consumerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User consumer;

    private final OffsetDateTime creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN,
    }
}
