package com.frello.models;

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
public class AdminLog {
    private final UUID id;
    private UUID adminId;
    private String logMessage;
    private final OffsetDateTime creationTime;
}
