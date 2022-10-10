package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.models.user.ServiceProviderActor;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceClass {
    private final UUID id;

    private UUID categoryId;
    private transient Optional<ServiceCategory> category;

    private UUID providerId;
    private transient Optional<ServiceProviderActor> provider;

    private String title;
    private String rawMarkdownPageBody;
    private String parsedHTMLPageBody;

    private boolean isDeleted;
    private Optional<OffsetDateTime> deletionTime;
    private final OffsetDateTime creationTime;
}
