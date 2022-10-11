package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerActor;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceReview {
    private final UUID id;

    private UUID serviceId;
    private Service service;

    private UUID consumerId;
    private ServiceConsumerActor consumer;

    short reviewScore;

    private String rawMarkdownBody;
    private String parsedHTMLBody;

    private boolean deleted;
    private OffsetDateTime deletionTime;
    private final OffsetDateTime creationTime;
}
