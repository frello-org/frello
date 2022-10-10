package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerActor;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceReview {
    private final UUID id;

    private UUID serviceId;
    private transient Optional<Service> service;

    private UUID consumerId;
    private transient Optional<ServiceConsumerActor> consumer;

    short reviewScore;

    private String rawMarkdownBody;
    private String parsedHTMLBody;

    private boolean deleted;
    private Optional<OffsetDateTime> deletionTime;
    private final OffsetDateTime creationTime;
}
