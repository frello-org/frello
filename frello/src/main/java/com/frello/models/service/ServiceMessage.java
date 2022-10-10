package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.frello.models.user.User;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceMessage {
    @NonNull
    private final UUID id;

    @NonNull
    private UUID serviceId;
    private Service service;

    @NonNull
    private UUID authorId;
    private User author;

    @NonNull
    private String rawMarkdownBody;
    @NonNull
    private String parsedHTMLBody;

    @NonNull
    private final OffsetDateTime creationTime;
}
