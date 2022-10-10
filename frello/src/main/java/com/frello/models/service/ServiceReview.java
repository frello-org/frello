package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

import com.frello.models.user.ServiceConsumerUser;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceReview {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID serviceId;
    private Service service;

    @NonNull
    private UUID consumerId;
    private ServiceConsumerUser consumer;

    short reviewScore;

    @NonNull
    private String rawMarkdownBody;
    @NonNull
    private String parsedHTMLBody;

    private boolean isDeleted;
    @NonNull
    private Instant deletedAt;
    @NonNull
    private final Instant creationTime;
}
