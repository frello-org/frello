package com.frello.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

import com.frello.models.user.AdminUser;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class AdminLog {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID adminId;
    private AdminUser admin;

    @NonNull
    private String logMessage;

    @NonNull
    private final Instant creationTime;
}
