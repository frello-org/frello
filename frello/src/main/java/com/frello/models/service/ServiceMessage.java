package com.frello.models.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import com.frello.models.user.User;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ServiceMessage {
    private final UUID id;

    private UUID serviceId;
    private transient Optional<Service> service;

    private UUID authorId;
    private transient Optional<User> author;

    private String rawMarkdownBody;
    private String parsedHTMLBody;

    private final OffsetDateTime creationTime;
}
