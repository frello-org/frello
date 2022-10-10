package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

import com.frello.models.user.ServiceProviderUser;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceClass {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID categoryId;
    private ServiceCategory category;

    @NonNull
    private UUID providerId;
    private ServiceProviderUser provider;

    @NonNull
    private String title;
    @NonNull
    private String rawMarkdownPageBody;
    @NonNull
    private String parsedHTMLPageBody;

    private boolean isDeleted;
    @NonNull
    private Instant deletionTime;
    @NonNull
    private final Instant creationTime;
}
