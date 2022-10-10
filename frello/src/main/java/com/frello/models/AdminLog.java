package com.frello.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.frello.models.user.AdminActor;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class AdminLog {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID adminId;
    private AdminActor admin;

    @NonNull
    private String logMessage;

    @NonNull
    private final OffsetDateTime creationTime;
}
