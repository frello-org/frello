package com.frello.models.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.frello.lib.InternalException;
import com.frello.models.user.User;
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
public class Service {
    @NonNull
    private final UUID id;

    @NonNull
    private State state;

    @NonNull
    private UUID requestId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ServiceRequest request;

    @NonNull
    private UUID providerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User provider;

    @NonNull
    private UUID consumerId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User consumer;

    private final OffsetDateTime creationTime;

    public enum State {
        IN_PROGRESS,
        COMPLETED,
        WITHDRAWN;

        public static State fromString(String raw) {
            return switch (raw.toUpperCase()) {
                case "IN_PROGRESS" -> State.IN_PROGRESS;
                case "COMPLETED" -> State.COMPLETED;
                case "WITHDRAWN" -> State.WITHDRAWN;
                default -> {
                    throw new InternalException("Invalid Service.State: " + raw);
                }
            };
        }
    }
}
