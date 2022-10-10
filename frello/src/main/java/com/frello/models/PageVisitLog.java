package com.frello.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

import com.frello.models.service.ServiceClass;
import com.frello.models.user.User;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class PageVisitLog {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID pageId;
    private ServiceClass page;

    @NonNull
    private UUID userId;
    private User user;

    @NonNull
    private final Instant creationTime;
}
